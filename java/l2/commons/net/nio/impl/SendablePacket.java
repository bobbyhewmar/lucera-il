package l2.commons.net.nio.impl;

import java.nio.ByteBuffer;

public abstract class SendablePacket<T extends MMOClient> extends l2.commons.net.nio.SendablePacket<T>
{
	@Override
	protected ByteBuffer getByteBuffer()
	{
		return getCurrentSelectorThread().getWriteBuffer();
	}
	
	@Override
	public T getClient()
	{
		return getCurrentSelectorThread().getWriteClient();
	}

	@SuppressWarnings("unchecked")
	private SelectorThread<T> getCurrentSelectorThread()
	{
		return (SelectorThread<T>) Thread.currentThread();
	}
	
	@Override
	protected abstract boolean write();
}
