package l2.gameserver.geodata;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;

final class GeoRegionLoader
{
	private static final int LUCERA_L2G_MAGIC = -2126429781;
	private static final int FLAT_BLOCK_SIZE = 3;
	private static final int COMPLEX_BLOCK_SIZE = 129;
	private static final int MIN_REGION_SIZE = 196608;
	private static final String FORMAT_L2G_LUCERA = "l2g_lucera_xor_v1";
	private static final String FORMAT_L2J_PLAIN = "l2j_plain";

	private GeoRegionLoader()
	{
	}

	static GeoRegionContent load(File geoFile) throws IOException, GeoDataFormatException
	{
		String geoName = geoFile.getName();
		byte[] decoded;
		if(geoName.endsWith(".l2j"))
		{
			decoded = decodePlainL2j(geoFile);
		}
		else if(geoName.endsWith(".l2g"))
		{
			decoded = decodeLuceraL2g(geoFile, geoName);
		}
		else
		{
			throw new GeoDataFormatException("file=" + geoName + ", reason=unsupported format");
		}
		return parse(decoded, geoName);
	}

	static GeoRegionContent parse(ByteBuffer source, String geoName) throws GeoDataFormatException
	{
		ByteBuffer geo = source.duplicate().order(ByteOrder.LITTLE_ENDIAN);
		geo.clear();
		byte[] decoded = new byte[geo.remaining()];
		geo.get(decoded);
		return parse(decoded, geoName);
	}

	private static GeoRegionContent parse(byte[] decoded, String geoName) throws GeoDataFormatException
	{
		if(decoded.length < MIN_REGION_SIZE)
		{
			throw new GeoDataFormatException("file=" + geoName + ", reason=unexpected size, decodedBytes=" + decoded.length + ", minimumBytes=" + MIN_REGION_SIZE);
		}

		byte[][] blocks = new byte[GeoEngine.BLOCKS_IN_MAP][];
		int index = 0;
		int maxLayers = 1;

		for(int blockIndex = 0;blockIndex < GeoEngine.BLOCKS_IN_MAP;++blockIndex)
		{
			ensureAvailable(decoded, geoName, index, 1, blockIndex, -1, -1, -1, "missing block type");

			int blockOffset = index;
			int blockType = decoded[index] & 0xFF;
			++index;

			switch(blockType)
			{
				case GeoEngine.BLOCKTYPE_FLAT:
				{
					ensureAvailable(decoded, geoName, index, 2, blockIndex, blockType, -1, -1, "flat block truncated");
					byte[] block = new byte[FLAT_BLOCK_SIZE];
					block[0] = (byte) blockType;
					System.arraycopy(decoded, index, block, 1, 2);
					index += 2;
					blocks[blockIndex] = block;
					break;
				}
				case GeoEngine.BLOCKTYPE_COMPLEX:
				{
					ensureAvailable(decoded, geoName, index, 128, blockIndex, blockType, -1, -1, "complex block truncated");
					byte[] block = new byte[COMPLEX_BLOCK_SIZE];
					block[0] = (byte) blockType;
					System.arraycopy(decoded, index, block, 1, 128);
					index += 128;
					blocks[blockIndex] = block;
					break;
				}
				case GeoEngine.BLOCKTYPE_MULTILEVEL:
				{
					int payloadStart = index;
					for(int cellIndex = 0;cellIndex < 64;++cellIndex)
					{
						ensureAvailable(decoded, geoName, index, 1, blockIndex, blockType, cellIndex, -1, "multilevel cell missing layer count");
						int layers = decoded[index] & 0xFF;
						maxLayers = Math.max(maxLayers, layers);
						ensureAvailable(decoded, geoName, index + 1, layers << 1, blockIndex, blockType, cellIndex, layers, "multilevel cell truncated");
						index += (layers << 1) + 1;
					}

					int payloadSize = index - payloadStart;
					byte[] block = new byte[payloadSize + 1];
					block[0] = (byte) blockType;
					System.arraycopy(decoded, payloadStart, block, 1, payloadSize);
					blocks[blockIndex] = block;
					break;
				}
				default:
				{
					throw new GeoDataFormatException("file=" + geoName + ", reason=unknown block type, offset=" + blockOffset + ", blockIndex=" + blockIndex + ", blockType=" + blockType);
				}
			}
		}

		if(index != decoded.length)
		{
			throw new GeoDataFormatException("file=" + geoName + ", reason=trailing bytes after region parse, consumedBytes=" + index + ", decodedBytes=" + decoded.length + ", trailingBytes=" + (decoded.length - index));
		}

		return new GeoRegionContent(ByteBuffer.wrap(decoded).order(ByteOrder.LITTLE_ENDIAN), blocks, maxLayers);
	}

	private static byte[] decodePlainL2j(File geoFile) throws IOException
	{
		return Files.readAllBytes(geoFile.toPath());
	}

	private static byte[] decodeLuceraL2g(File geoFile, String geoName) throws IOException, GeoDataFormatException
	{
		byte[] raw = Files.readAllBytes(geoFile.toPath());
		if(raw.length < 4)
		{
			throw new GeoDataFormatException("file=" + geoName + ", format=" + FORMAT_L2G_LUCERA + ", reason=missing encrypted payload header, bytes=" + raw.length);
		}

		ByteBuffer headerBuffer = ByteBuffer.wrap(raw, 0, 4).order(ByteOrder.LITTLE_ENDIAN);
		int header = headerBuffer.getInt();
		int checkSum = LUCERA_L2G_MAGIC ^ header;
		byte[] decoded = new byte[raw.length - 4];
		System.arraycopy(raw, 4, decoded, 0, decoded.length);

		byte key = (byte) ((checkSum >>> 24 & 0xFF) ^ (checkSum >>> 16 & 0xFF) ^ (checkSum >>> 8 & 0xFF) ^ (checkSum & 0xFF));
		for(int i = 0;i < decoded.length;++i)
		{
			decoded[i] = (byte) (decoded[i] ^ key);
			key = decoded[i];
			checkSum -= key;
		}

		if(checkSum != 0)
		{
			throw new GeoDataFormatException("file=" + geoName + ", format=" + FORMAT_L2G_LUCERA + ", reason=checksum mismatch, header=0x" + Integer.toUnsignedString(header, 16).toUpperCase() + ", finalChecksum=" + checkSum);
		}

		return decoded;
	}

	private static void ensureAvailable(byte[] decoded, String geoName, int offset, int requestedBytes, int blockIndex, int blockType, int cellIndex, int layers, String reason) throws GeoDataFormatException
	{
		if(offset < 0 || requestedBytes < 0 || offset + requestedBytes > decoded.length)
		{
			throw new GeoDataFormatException("file=" + geoName + ", reason=" + reason + ", offset=" + offset + ", requestedBytes=" + requestedBytes + ", availableBytes=" + Math.max(0, decoded.length - offset) + ", blockIndex=" + blockIndex + ", blockType=" + blockType + ", cellIndex=" + cellIndex + ", layers=" + layers);
		}
	}

	static final class GeoRegionContent
	{
		private final ByteBuffer rawBuffer;
		private final byte[][] blocks;
		private final int maxLayers;

		private GeoRegionContent(ByteBuffer rawBuffer, byte[][] blocks, int maxLayers)
		{
			this.rawBuffer = rawBuffer;
			this.blocks = blocks;
			this.maxLayers = maxLayers;
		}

		ByteBuffer getRawBuffer()
		{
			return rawBuffer;
		}

		byte[][] getBlocks()
		{
			return blocks;
		}

		int getMaxLayers()
		{
			return maxLayers;
		}
	}

	static final class GeoDataFormatException extends Exception
	{
		private GeoDataFormatException(String message)
		{
			super(message);
		}
	}
}
