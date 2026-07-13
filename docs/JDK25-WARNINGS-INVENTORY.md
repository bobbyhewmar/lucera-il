# Inventario Completo de Warnings JDK 25

- Origem: compilacao com `ant -f Full-build.xml commons-jar gameserver-jar compile-scripts compile-authserver` usando JDK 25.
- Metodo: warnings deduplicados por `arquivo + linha + categoria + mensagem + detalhes`.
- Total deduplicado: 274.

## (classpath) (1)
- [path] sem linha: bad path element "D:\Jogos\Lineage II\Servidores\Lucera\Souce\main\dist\libs\server.jar": no such file or directory

## java\l2\authserver\AuthServer.java (3)
- [rawtypes] L31: found raw type: SelectorThread | _selectorThread = new SelectorThread(sc, loginPacketHandler, sh, sh, sh); ; ^ ; missing type arguments for generic class SelectorThread<T> ; where T is a type-variable: ; T extends MMOClient declared in class SelectorThread
- [unchecked] L31: unchecked call to SelectorThread(SelectorConfig,IPacketHandler<T>,IMMOExecutor<T>,IClientFactory<T>,IAcceptFilter) as a member of the raw type SelectorThread | _selectorThread = new SelectorThread(sc, loginPacketHandler, sh, sh, sh); ; ^ ; where T is a type-variable: ; T extends MMOClient declared in class SelectorThread
- [unchecked] L31: unchecked conversion | _selectorThread = new SelectorThread(sc, loginPacketHandler, sh, sh, sh); ; ^ ; required: SelectorThread<L2LoginClient> ; found:    SelectorThread

## java\l2\commons\collections\EmptyIterator.java (1)
- [unchecked] L15: unchecked conversion | return INSTANCE; ; ^ ; required: Iterator<E> ; found:    Iterator ; where E is a type-variable: ; E extends Object declared in method <E>getInstance()

## java\l2\commons\collections\JoinedIterator.java (1)
- [unchecked] L24: unchecked conversion | _iterators = iterators; ; ^ ; required: Iterator<E>[] ; found:    Iterator[] ; where E is a type-variable: ; E extends Object declared in class JoinedIterator

## java\l2\commons\collections\LazyArrayList.java (9)
- [unchecked] L58: unchecked conversion | return (LazyArrayList) POOL.borrowObject(); ; ^ ; required: LazyArrayList<E> ; found:    LazyArrayList ; where E is a type-variable: ; E extends Object declared in method <E>newInstance()
- [unchecked] L63: unchecked conversion | return new LazyArrayList(); ; ^ ; required: LazyArrayList<E> ; found:    LazyArrayList ; where E is a type-variable: ; E extends Object declared in method <E>newInstance()
- [unchecked] L93: unchecked cast | e = (E) elementData[index]; ; ^ ; required: E ; found:    Object ; where E is a type-variable: ; E extends Object declared in class LazyArrayList
- [unchecked] L183: unchecked cast | e = (E) elementData[index]; ; ^ ; required: E ; found:    Object ; where E is a type-variable: ; E extends Object declared in class LazyArrayList
- [unchecked] L302: unchecked cast | return size > 0 && index >= 0 && index < size ? (E) elementData[index] : null; ; ^ ; required: E ; found:    Object ; where E is a type-variable: ; E extends Object declared in class LazyArrayList
- [unchecked] L462: unchecked cast | T[] r = a.length >= size ? a : (T[]) Array.newInstance(a.getClass().getComponentType(), size); ; ^ ; required: T[] ; found:    Object ; where T is a type-variable: ; T extends Object declared in method <T>toArray(T[])
- [unchecked] L479: unchecked conversion | return new LazyArrayList.LazyItr(); ; ^ ; required: Iterator<E> ; found:    LazyArrayList.LazyItr ; where E is a type-variable: ; E extends Object declared in class LazyArrayList
- [unchecked] L485: unchecked conversion | return new LazyArrayList.LazyListItr(0); ; ^ ; required: ListIterator<E> ; found:    LazyArrayList.LazyListItr ; where E is a type-variable: ; E extends Object declared in class LazyArrayList
- [unchecked] L491: unchecked conversion | return new LazyArrayList.LazyListItr(index); ; ^ ; required: ListIterator<E> ; found:    LazyArrayList.LazyListItr ; where E is a type-variable: ; E extends Object declared in class LazyArrayList

## java\l2\commons\collections\MultiValueSet.java (2)
- [unchecked] L300: unchecked cast | return (E) val; ; ^ ; required: E ; found:    Object ; where E,T are type-variables: ; E extends Enum<E> declared in method <E>getEnum(T,Class<E>) ; T extends Object declared in class MultiValueSet
- [unchecked] L314: unchecked cast | return (E) val; ; ^ ; required: E ; found:    Object ; where E,T are type-variables: ; E extends Enum<E> declared in method <E>getEnum(T,Class<E>,E) ; T extends Object declared in class MultiValueSet

## java\l2\commons\lang\ArrayUtils.java (6)
- [unchecked] L27: unchecked cast | T[] newArray = (T[]) copyArrayGrow(array, type); ; ^ ; required: T[] ; found:    Object[] ; where T is a type-variable: ; T extends Object declared in method <T>add(T[],T)
- [unchecked] L27: unchecked conversion | T[] newArray = (T[]) copyArrayGrow(array, type); ; ^ ; required: Class<? extends T> ; found:    Class ; where T is a type-variable: ; T extends Object declared in method <T>copyArrayGrow(T[],Class<? extends T>)
- [unchecked] L27: unchecked method invocation: method copyArrayGrow in class ArrayUtils is applied to given types | T[] newArray = (T[]) copyArrayGrow(array, type); ; ^ ; required: T#1[],Class<? extends T#1> ; found:    T#2[],Class ; where T#1,T#2 are type-variables: ; T#1 extends Object declared in method <T#1>copyArrayGrow(T#1[],Class<? extends T#1>) ; T#2 extends Object declared in method <T#2>add(T#2[],T#2)
- [unchecked] L39: unchecked cast | return (T[]) newArray; ; ^ ; required: T[] ; found:    Object[] ; where T is a type-variable: ; T extends Object declared in method <T>copyArrayGrow(T[],Class<? extends T>)
- [unchecked] L41: unchecked cast | return (T[]) Array.newInstance(type, 1); ; ^ ; required: T[] ; found:    Object ; where T is a type-variable: ; T extends Object declared in method <T>copyArrayGrow(T[],Class<? extends T>)
- [unchecked] L92: unchecked cast | return (T[]) newArray; ; ^ ; required: T[] ; found:    Object[] ; where T is a type-variable: ; T extends Object declared in method <T>remove(T[],T)

## java\l2\commons\lang\reference\HardReferences.java (1)
- [unchecked] L17: unchecked cast | return (HardReference<T>) EMPTY_REF; ; ^ ; required: HardReference<T> ; found:    HardReference<CAP#1> ; where T is a type-variable: ; T extends Object declared in method <T>emptyRef() ; where CAP#1 is a fresh type-variable: ; CAP#1 extends Object from capture of ?

## java\l2\commons\net\nio\impl\MMOConnection.java (1)
- [unchecked] L86: Possible heap pollution from parameterized vararg type SendablePacket<T> | public void sendPacket(SendablePacket<T>... args) ; ^ ; where T is a type-variable: ; T extends MMOClient declared in class MMOConnection

## java\l2\commons\net\nio\impl\SelectorThread.java (14)
- [unchecked] L26: unchecked conversion | private static final List<SelectorThread> ALL_SELECTORS = new ArrayList(); ; ^ ; required: List<SelectorThread> ; found:    ArrayList
- [unchecked] L57: unchecked conversion | _bufferPool = new ArrayDeque(_sc.HELPER_BUFFER_COUNT); ; ^ ; required: Queue<ByteBuffer> ; found:    ArrayDeque
- [unchecked] L58: unchecked conversion | _connections = new CopyOnWriteArrayList(); ; ^ ; required: List<MMOConnection<T>> ; found:    CopyOnWriteArrayList ; where T is a type-variable: ; T extends MMOClient declared in class SelectorThread
- [unchecked] L240: unchecked conversion | MMOConnection<T> con = (MMOConnection) key.attachment(); ; ^ ; required: MMOConnection<T> ; found:    MMOConnection ; where T is a type-variable: ; T extends MMOClient declared in class SelectorThread
- [unchecked] L243: unchecked conversion | closeConnectionImpl(client.getConnection()); ; ^ ; required: MMOConnection<T> ; found:    MMOConnection ; where T is a type-variable: ; T extends MMOClient declared in class SelectorThread
- [unchecked] L243: unchecked method invocation: method closeConnectionImpl in class SelectorThread is applied to given types | closeConnectionImpl(client.getConnection()); ; ^ ; required: MMOConnection<T> ; found:    MMOConnection ; where T is a type-variable: ; T extends MMOClient declared in class SelectorThread
- [unchecked] L264: unchecked call to MMOConnection(SelectorThread<T>,Socket,SelectionKey) as a member of the raw type MMOConnection | MMOConnection<T> con = new MMOConnection(this, sc.socket(), clientKey); ; ^ ; where T is a type-variable: ; T extends MMOClient declared in class MMOConnection
- [unchecked] L264: unchecked conversion | MMOConnection<T> con = new MMOConnection(this, sc.socket(), clientKey); ; ^ ; required: MMOConnection<T> ; found:    MMOConnection ; where T is a type-variable: ; T extends MMOClient declared in class SelectorThread
- [unchecked] L266: unchecked call to setConnection(T) as a member of the raw type MMOClient | client.setConnection(con); ; ^ ; where T is a type-variable: ; T extends MMOConnection declared in class MMOClient
- [unchecked] L282: unchecked conversion | MMOConnection<T> con = (MMOConnection) key.attachment(); ; ^ ; required: MMOConnection<T> ; found:    MMOConnection ; where T is a type-variable: ; T extends MMOClient declared in class SelectorThread
- [unchecked] L431: unchecked conversion | MMOConnection<T> con = (MMOConnection) key.attachment(); ; ^ ; required: MMOConnection<T> ; found:    MMOConnection ; where T is a type-variable: ; T extends MMOClient declared in class SelectorThread
- [unchecked] L502: unchecked conversion | putPacketIntoWriteBuffer(sp, true); ; ^ ; required: SendablePacket<T> ; found:    SendablePacket ; where T is a type-variable: ; T extends MMOClient declared in class SelectorThread
- [unchecked] L502: unchecked method invocation: method putPacketIntoWriteBuffer in class SelectorThread is applied to given types | putPacketIntoWriteBuffer(sp, true); ; ^ ; required: SendablePacket<T>,boolean ; found:    SendablePacket,boolean ; where T is a type-variable: ; T extends MMOClient declared in class SelectorThread
- [unchecked] L604: unchecked call to setConnection(T) as a member of the raw type MMOClient | con.getClient().setConnection(null); ; ^ ; where T is a type-variable: ; T extends MMOConnection declared in class MMOClient

## java\l2\commons\net\nio\impl\SendablePacket.java (1)
- [unchecked] L16: unchecked cast | return (T) ((SelectorThread) Thread.currentThread()).getWriteClient(); ; ^ ; required: T ; found:    MMOClient ; where T is a type-variable: ; T extends MMOClient declared in class SendablePacket

## java\l2\commons\text\StrTable.java (3)
- [unchecked] L120: unchecked conversion | rows.put(rowIndex, row); ; ^ ; required: V ; found:    Map ; where V is a type-variable: ; V extends Object declared in interface Map
- [unchecked] L120: unchecked method invocation: method put in interface Map is applied to given types | rows.put(rowIndex, row); ; ^ ; required: K,V ; found:    int,Map ; where K,V are type-variables: ; K extends Object declared in interface Map ; V extends Object declared in interface Map
- [unchecked] L122: unchecked call to put(K,V) as a member of the raw type Map | row.put(colName, val); ; ^ ; where K,V are type-variables: ; K extends Object declared in interface Map ; V extends Object declared in interface Map

## java\l2\commons\threading\RunnableStatsManager.java (1)
- [unchecked] L14: unchecked conversion | private final Map<Class<?>, ClassStat> classStats = new HashMap(); ; ^ ; required: Map<Class<?>,RunnableStatsManager.ClassStat> ; found:    HashMap

## java\l2\commons\threading\SteppingRunnableQueueManager.java (4)
- [unchecked] L23: unchecked conversion | private final List<SteppingScheduledFuture<?>> queue = new CopyOnWriteArrayList(); ; ^ ; required: List<SteppingRunnableQueueManager.SteppingScheduledFuture<?>> ; found:    CopyOnWriteArrayList
- [unchecked] L96: unchecked call to add(E) as a member of the raw type LazyArrayList | purge.add(sr); ; ^ ; where E is a type-variable: ; E extends Object declared in class LazyArrayList
- [unchecked] L99: unchecked conversion | LazyArrayList.recycle(purge); ; ^ ; required: LazyArrayList<E> ; found:    LazyArrayList ; where E is a type-variable: ; E extends Object declared in method <E>recycle(LazyArrayList<E>)
- [unchecked] L99: unchecked method invocation: method recycle in class LazyArrayList is applied to given types | LazyArrayList.recycle(purge); ; ^ ; required: LazyArrayList<E> ; found:    LazyArrayList ; where E is a type-variable: ; E extends Object declared in method <E>recycle(LazyArrayList<E>)

## java\l2\commons\time\cron\SchedulingPattern.java (21)
- [unchecked] L32: unchecked conversion | protected List<SchedulingPattern.ValueMatcher> minuteMatchers = new ArrayList(); ; ^ ; required: List<ValueMatcher> ; found:    ArrayList
- [unchecked] L33: unchecked conversion | protected List<SchedulingPattern.ValueMatcher> hourMatchers = new ArrayList(); ; ^ ; required: List<ValueMatcher> ; found:    ArrayList
- [unchecked] L34: unchecked conversion | protected List<SchedulingPattern.ValueMatcher> dayOfMonthMatchers = new ArrayList(); ; ^ ; required: List<ValueMatcher> ; found:    ArrayList
- [unchecked] L35: unchecked conversion | protected List<SchedulingPattern.ValueMatcher> monthMatchers = new ArrayList(); ; ^ ; required: List<ValueMatcher> ; found:    ArrayList
- [unchecked] L36: unchecked conversion | protected List<SchedulingPattern.ValueMatcher> dayOfWeekMatchers = new ArrayList(); ; ^ ; required: List<ValueMatcher> ; found:    ArrayList
- [unchecked] L38: unchecked conversion | protected Map<Integer, Integer> hourAdder = new TreeMap(); ; ^ ; required: Map<Integer,Integer> ; found:    TreeMap
- [unchecked] L39: unchecked conversion | protected Map<Integer, Integer> hourAdderRnd = new TreeMap(); ; ^ ; required: Map<Integer,Integer> ; found:    TreeMap
- [unchecked] L40: unchecked conversion | protected Map<Integer, Integer> dayOfYearAdder = new TreeMap(); ; ^ ; required: Map<Integer,Integer> ; found:    TreeMap
- [unchecked] L41: unchecked conversion | protected Map<Integer, Integer> minuteAdderRnd = new TreeMap(); ; ^ ; required: Map<Integer,Integer> ; found:    TreeMap
- [unchecked] L42: unchecked conversion | protected Map<Integer, Integer> weekOfYearAdder = new TreeMap(); ; ^ ; required: Map<Integer,Integer> ; found:    TreeMap
- [unchecked] L236: unchecked conversion | List<Integer> values = new ArrayList(); ; ^ ; required: List<Integer> ; found:    ArrayList
- [unchecked] L320: unchecked conversion | List<Integer> values2 = new ArrayList(); ; ^ ; required: List<Integer> ; found:    ArrayList
- [unchecked] L345: unchecked conversion | List<Integer> values = new ArrayList(); ; ^ ; required: List<Integer> ; found:    ArrayList
- [removal] L349: Integer(int) in Integer has been deprecated and marked for removal | values.add(new Integer(v1)); ; ^
- [unchecked] L373: unchecked conversion | List<Integer> values = new ArrayList(); ; ^ ; required: List<Integer> ; found:    ArrayList
- [removal] L374: Integer(int) in Integer has been deprecated and marked for removal | values.add(new Integer(v1)); ; ^
- [unchecked] L391: unchecked conversion | List<Integer> values = new ArrayList(); ; ^ ; required: List<Integer> ; found:    ArrayList
- [removal] L397: Integer(int) in Integer has been deprecated and marked for removal | values.add(new Integer(min)); ; ^
- [removal] L408: Integer(int) in Integer has been deprecated and marked for removal | values.add(new Integer(i)); ; ^
- [removal] L413: Integer(int) in Integer has been deprecated and marked for removal | values.add(new Integer(i)); ; ^
- [removal] L418: Integer(int) in Integer has been deprecated and marked for removal | values.add(new Integer(v1)); ; ^

## java\l2\commons\util\concurrent\locks\ReentrantReadWriteLock.java (4)
- [deprecation] L111: getId() in Thread has been deprecated | if(rh == null ;; rh.tid != current.getId()) ; ^
- [deprecation] L123: getId() in Thread has been deprecated | if(rh == null ;; rh.tid != current.getId()) ; ^
- [deprecation] L164: getId() in Thread has been deprecated | if(rh == null ;; rh.tid != current.getId()) ; ^
- [deprecation] L180: getId() in Thread has been deprecated | final long tid = Thread.currentThread().getId(); ; ^

## java\l2\commons\util\TroveUtils.java (1)
- [unchecked] L13: unchecked conversion | return EMPTY_INT_OBJECT_MAP; ; ^ ; required: TIntObjectHashMap<V> ; found:    TIntObjectHashMap ; where V is a type-variable: ; V extends Object declared in method <V>emptyIntObjectMap()

## java\l2\commons\versioning\Locator.java (1)
- [deprecation] L53: URL(String) in URL has been deprecated | url = new URL(uri); ; ^

## java\l2\gameserver\model\actor\listener\CharListenerList.java (1)
- [unchecked] L22: unchecked conversion | static final ListenerList<Creature> global = new ListenerList(); ; ^ ; required: ListenerList<Creature> ; found:    ListenerList

## java\l2\gameserver\model\AggroList.java (1)
- [unchecked] L22: unchecked conversion | private final TIntObjectHashMap<AggroInfo> hateList = new TIntObjectHashMap(); ; ^ ; required: TIntObjectHashMap<AggroList.AggroInfo> ; found:    TIntObjectHashMap

## java\l2\gameserver\model\CommandChannel.java (3)
- [deprecation] L43: Msg in l2.gameserver.cache has been deprecated | creator.sendPacket(Msg.YOU_DO_NOT_HAVE_AUTHORITY_TO_USE_THE_COMMAND_CHANNEL); ; ^
- [deprecation] L51: Msg in l2.gameserver.cache has been deprecated | creator.sendPacket(Msg.YOU_DO_NOT_HAVE_AUTHORITY_TO_USE_THE_COMMAND_CHANNEL); ; ^
- [deprecation] L104: Msg in l2.gameserver.cache has been deprecated | broadCast(Msg.THE_COMMAND_CHANNEL_HAS_BEEN_DISBANDED); ; ^

## java\l2\gameserver\model\Creature.java (10)
- [unchecked] L115: unchecked conversion | protected IntObjectMap<TimeStamp> _skillReuses = new CHashIntObjectMap(); ; ^ ; required: IntObjectMap<TimeStamp> ; found:    CHashIntObjectMap
- [deprecation] L246: Msg in l2.gameserver.cache has been deprecated | sendPacket(Msg.CASTING_HAS_BEEN_INTERRUPTED); ; ^
- [deprecation] L491: Msg in l2.gameserver.cache has been deprecated | sendPacket(Msg.NOT_ENOUGH_MP); ; ^
- [deprecation] L545: Msg in l2.gameserver.cache has been deprecated | sendPacket(Msg.THERE_IS_NOT_ENOUGHT_SOUL); ; ^
- [deprecation] L1285: Msg in l2.gameserver.cache has been deprecated | sendPacket(Msg.SUMMON_A_PET); ; ^
- [deprecation] L1306: Msg in l2.gameserver.cache has been deprecated | sendPacket(Msg.NOT_ENOUGH_MP); ; ^
- [deprecation] L2496: Msg in l2.gameserver.cache has been deprecated | player.sendPacket(Msg.INVALID_TARGET); ; ^
- [deprecation] L2586: Msg in l2.gameserver.cache has been deprecated | sendPacket(Msg.NOT_ENOUGH_MP); ; ^
- [deprecation] L2662: Msg in l2.gameserver.cache has been deprecated | attacker.sendPacket(Msg.THE_ATTACK_HAS_BEEN_BLOCKED); ; ^
- [deprecation] L2742: Msg in l2.gameserver.cache has been deprecated | attacker.sendPacket(Msg.THE_ATTACK_HAS_BEEN_BLOCKED); ; ^

## java\l2\gameserver\model\entity\events\EventOwner.java (2)
- [unchecked] L17: unchecked cast | return (E) e; ; ^ ; required: E ; found:    GlobalEvent ; where E is a type-variable: ; E extends GlobalEvent declared in method <E>getEvent(Class<E>)
- [unchecked] L21: unchecked cast | return (E) e; ; ^ ; required: E ; found:    GlobalEvent ; where E is a type-variable: ; E extends GlobalEvent declared in method <E>getEvent(Class<E>)

## java\l2\gameserver\model\entity\Reflection.java (10)
- [deprecation] L584: SimpleSpawner in l2.gameserver.model has been deprecated | public void addSpawn(SimpleSpawner spawn) ; ^
- [deprecation] L592: SpawnInfo in InstantZone has been deprecated | public void fillSpawns(List<InstantZone.SpawnInfo> si) ; ^
- [deprecation] L599: SpawnInfo in InstantZone has been deprecated | for(InstantZone.SpawnInfo s : si) ; ^
- [deprecation] L601: SimpleSpawner in l2.gameserver.model has been deprecated | SimpleSpawner c; ; ^
- [deprecation] L608: SimpleSpawner in l2.gameserver.model has been deprecated | c = new SimpleSpawner(s.getNpcId()); ; ^
- [deprecation] L628: SimpleSpawner in l2.gameserver.model has been deprecated | c = new SimpleSpawner(s.getNpcId()); ; ^
- [deprecation] L647: SimpleSpawner in l2.gameserver.model has been deprecated | c = new SimpleSpawner(s.getNpcId()); ; ^
- [unchecked] L674: unchecked conversion | _doors = new HashIntObjectMap(doors.size()); ; ^ ; required: IntObjectMap<DoorInstance> ; found:    HashIntObjectMap
- [unchecked] L724: unchecked conversion | _doors = new HashIntObjectMap(doors.size()); ; ^ ; required: IntObjectMap<DoorInstance> ; found:    HashIntObjectMap
- [deprecation] L830: SimpleSpawner in l2.gameserver.model has been deprecated | SimpleSpawner sp = new SimpleSpawner(NpcHolder.getInstance().getTemplate(npcId)); ; ^

## java\l2\gameserver\model\entity\residence\Castle.java (1)
- [unchecked] L46: unchecked conversion | private final IntObjectMap<MerchantGuard> _merchantGuards = new HashIntObjectMap(); ; ^ ; required: IntObjectMap<MerchantGuard> ; found:    HashIntObjectMap

## java\l2\gameserver\model\entity\residence\Residence.java (1)
- [unchecked] L90: unchecked cast | return (E) _siegeEvent; ; ^ ; required: E ; found:    SiegeEvent<CAP#1,CAP#2> ; where E is a type-variable: ; E extends SiegeEvent declared in method <E>getSiegeEvent() ; where CAP#1,CAP#2 are fresh type-variables: ; CAP#1 extends Residence from capture of ? ; CAP#2 extends SiegeClanObject from capture of ?

## java\l2\gameserver\model\instances\MonsterInstance.java (4)
- [unchecked] L377: unchecked conversion | players = new ArrayList(killer.getParty().getMemberCount()); ; ^ ; required: ArrayList<Player> ; found:    ArrayList
- [unchecked] L502: unchecked conversion | rollRewards(entry, lastAttacker, topDamager); ; ^ ; required: Entry<RewardType,RewardList> ; found:    Entry
- [unchecked] L502: unchecked method invocation: method rollRewards in class MonsterInstance is applied to given types | rollRewards(entry, lastAttacker, topDamager); ; ^ ; required: Entry<RewardType,RewardList>,Creature,Creature ; found:    Entry,Creature,Creature
- [deprecation] L818: Msg in l2.gameserver.cache has been deprecated | killer.sendPacket(Msg.OVER_HIT, new SystemMessage(362).addNumber(overHitExp)); ; ^

## java\l2\gameserver\model\instances\NpcInstance.java (2)
- [unchecked] L195: unchecked cast | return (HardReference<NpcInstance>) super.getRef(); ; ^ ; required: HardReference<NpcInstance> ; found:    HardReference<CAP#1> ; where CAP#1 is a fresh type-variable: ; CAP#1 extends Creature from capture of ? extends Creature
- [unchecked] L1777: unchecked conversion | _parameters = new MultiValueSet(set.size()); ; ^ ; required: MultiValueSet<String> ; found:    MultiValueSet

## java\l2\gameserver\model\items\ItemInstance.java (1)
- [unchecked] L59: unchecked conversion | private final AtomicEnumBitFlag<ItemStateFlags> _stateFlags = new AtomicEnumBitFlag(); ; ^ ; required: AtomicEnumBitFlag<ItemStateFlags> ; found:    AtomicEnumBitFlag

## java\l2\gameserver\model\Party.java (3)
- [deprecation] L363: Msg in l2.gameserver.cache has been deprecated | pplayer.add(Msg.YOU_HAVE_BEEN_EXPELLED_FROM_THE_PARTY); ; ^
- [deprecation] L367: Msg in l2.gameserver.cache has been deprecated | pplayer.add(Msg.YOU_HAVE_WITHDRAWN_FROM_THE_PARTY); ; ^
- [unchecked] L522: unchecked call to add(E) as a member of the raw type List | ret.add(member); ; ^ ; where E is a type-variable: ; E extends Object declared in interface List

## java\l2\gameserver\model\Playable.java (14)
- [unchecked] L54: unchecked cast | return (HardReference<? extends Playable>) super.getRef(); ; ^ ; required: HardReference<? extends Playable> ; found:    HardReference<CAP#1> ; where CAP#1 is a fresh type-variable: ; CAP#1 extends Creature from capture of ? extends Creature
- [deprecation] L136: Msg in l2.gameserver.cache has been deprecated | player.sendPacket(Msg.INVALID_TARGET); ; ^
- [deprecation] L141: Msg in l2.gameserver.cache has been deprecated | player.sendPacket(Msg.YOUR_TARGET_IS_OUT_OF_RANGE); ; ^
- [deprecation] L146: Msg in l2.gameserver.cache has been deprecated | player.sendPacket(Msg.INVALID_TARGET); ; ^
- [deprecation] L164: Msg in l2.gameserver.cache has been deprecated | player.sendPacket(Msg.INVALID_TARGET); ; ^
- [deprecation] L171: Msg in l2.gameserver.cache has been deprecated | player.sendPacket(Msg.INVALID_TARGET); ; ^
- [deprecation] L176: Msg in l2.gameserver.cache has been deprecated | player.sendPacket(Msg.YOU_MAY_NOT_ATTACK_THIS_TARGET_IN_A_PEACEFUL_ZONE); ; ^
- [deprecation] L277: Msg in l2.gameserver.cache has been deprecated | player.sendPacket(Msg.NOT_ENOUGH_MP); ; ^
- [deprecation] L286: Msg in l2.gameserver.cache has been deprecated | player.sendPacket(Msg.YOU_HAVE_RUN_OUT_OF_ARROWS); ; ^
- [deprecation] L314: Msg in l2.gameserver.cache has been deprecated | getPlayer().sendPacket(Msg.YOU_MAY_NOT_ATTACK_IN_A_PEACEFUL_ZONE); ; ^
- [deprecation] L319: Msg in l2.gameserver.cache has been deprecated | getPlayer().sendPacket(Msg.INVALID_TARGET); ; ^
- [deprecation] L340: Msg in l2.gameserver.cache has been deprecated | attacker.sendPacket(Msg.THE_ATTACK_HAS_BEEN_BLOCKED); ; ^
- [deprecation] L352: Msg in l2.gameserver.cache has been deprecated | pcAttacker.sendPacket(Msg.INVALID_TARGET); ; ^
- [deprecation] L360: Msg in l2.gameserver.cache has been deprecated | attacker.getPlayer().sendPacket(Msg.INVALID_TARGET); ; ^

## java\l2\gameserver\model\Player.java (12)
- [unchecked] L830: unchecked cast | return (HardReference<Player>) super.getRef(); ; ^ ; required: HardReference<Player> ; found:    HardReference<CAP#1> ; where CAP#1 is a fresh type-variable: ; CAP#1 extends Playable from capture of ? extends Playable
- [deprecation] L1299: Msg in l2.gameserver.cache has been deprecated | sendPacket(Msg.PROGRESS_IN_A_QUEST_IS_POSSIBLE_ONLY_WHEN_YOUR_INVENTORYS_WEIGHT_AND_VOLUME_ARE_LESS_THAN_80_PERCENT_OF_CAPACITY); ; ^
- [deprecation] L3288: Msg in l2.gameserver.cache has been deprecated | sendPacket(Msg.YOU_HAVE_INCREASED_YOUR_LEVEL); ; ^
- [deprecation] L4864: Msg in l2.gameserver.cache has been deprecated | sendPacket(Msg.YOU_HAVE_FAILED_TO_REGISTER_THE_USER_TO_YOUR_IGNORE_LIST); ; ^
- [deprecation] L4870: Msg in l2.gameserver.cache has been deprecated | sendPacket(Msg.YOU_MAY_NOT_IMPOSE_A_BLOCK_ON_A_GM); ; ^
- [deprecation] L4880: Msg in l2.gameserver.cache has been deprecated | sendPacket(Msg.YOU_HAVE_FAILED_TO_REGISTER_THE_USER_TO_YOUR_IGNORE_LIST); ; ^
- [deprecation] L4884: Msg in l2.gameserver.cache has been deprecated | sendPacket(Msg.YOU_MAY_NOT_IMPOSE_A_BLOCK_ON_A_GM); ; ^
- [deprecation] L4902: Msg in l2.gameserver.cache has been deprecated | sendPacket(Msg.YOU_HAVE_FAILED_TO_DELETE_THE_CHARACTER_FROM_IGNORE_LIST); ; ^
- [deprecation] L5488: Msg in l2.gameserver.cache has been deprecated | reviver.sendPacket(Msg.BETTER_RESURRECTION_HAS_BEEN_ALREADY_PROPOSED); ; ^
- [deprecation] L5492: Msg in l2.gameserver.cache has been deprecated | reviver.sendPacket(Msg.SINCE_THE_MASTER_WAS_IN_THE_PROCESS_OF_BEING_RESURRECTED_THE_ATTEMPT_TO_RESURRECT_THE_PET_HAS_BEEN_CANCELLED); ; ^
- [deprecation] L5496: Msg in l2.gameserver.cache has been deprecated | reviver.sendPacket(Msg.WHILE_A_PET_IS_ATTEMPTING_TO_RESURRECT_IT_CANNOT_HELP_IN_RESURRECTING_ITS_MASTER); ; ^
- [deprecation] L6325: Msg in l2.gameserver.cache has been deprecated | sendPacket(Msg.SOUL_CANNOT_BE_ABSORBED_ANY_MORE); ; ^

## java\l2\gameserver\model\pledge\Clan.java (8)
- [unchecked] L88: unchecked conversion | protected IntObjectMap<Skill> _skills = new CTreeIntObjectMap(); ; ^ ; required: IntObjectMap<Skill> ; found:    CTreeIntObjectMap
- [unchecked] L89: unchecked conversion | protected IntObjectMap<RankPrivs> _privs = new CTreeIntObjectMap(); ; ^ ; required: IntObjectMap<RankPrivs> ; found:    CTreeIntObjectMap
- [unchecked] L90: unchecked conversion | protected IntObjectMap<SubUnit> _subUnits = new CTreeIntObjectMap(); ; ^ ; required: IntObjectMap<SubUnit> ; found:    CTreeIntObjectMap
- [deprecation] L774: Msg in l2.gameserver.cache has been deprecated | broadcastToOnlineMembers(Msg.SINCE_THE_CLAN_REPUTATION_SCORE_HAS_DROPPED_TO_0_OR_LOWER_YOUR_CLAN_SKILLS_WILL_BE_DE_ACTIVATED); ; ^
- [deprecation] L784: Msg in l2.gameserver.cache has been deprecated | broadcastToOnlineMembers(Msg.THE_CLAN_SKILL_WILL_BE_ACTIVATED_BECAUSE_THE_CLANS_REPUTATION_SCORE_HAS_REACHED_TO_0_OR_HIGHER); ; ^
- [deprecation] L1055: Msg in l2.gameserver.cache has been deprecated | player.sendPacket(Msg.YOUR_CLAN_HAS_ALREADY_ESTABLISHED_A_CLAN_ACADEMY); ; ^
- [deprecation] L1074: Msg in l2.gameserver.cache has been deprecated | player.sendPacket(Msg.THE_CLAN_REPUTATION_SCORE_IS_TOO_LOW); ; ^
- [deprecation] L1087: Msg in l2.gameserver.cache has been deprecated | player.sendPacket(Msg.THE_CLAN_REPUTATION_SCORE_IS_TOO_LOW); ; ^

## java\l2\gameserver\model\pledge\SubUnit.java (2)
- [unchecked] L23: unchecked conversion | private final IntObjectMap<Skill> _skills = new CTreeIntObjectMap(); ; ^ ; required: IntObjectMap<Skill> ; found:    CTreeIntObjectMap
- [unchecked] L24: unchecked conversion | private final IntObjectMap<UnitMember> _members = new CHashIntObjectMap(); ; ^ ; required: IntObjectMap<UnitMember> ; found:    CHashIntObjectMap

## java\l2\gameserver\model\quest\Quest.java (2)
- [unchecked] L407: unchecked conversion | _npcLogList = new TIntObjectHashMap(5); ; ^ ; required: TIntObjectHashMap<List<QuestNpcLogInfo>> ; found:    TIntObjectHashMap
- [deprecation] L887: spawn(Location,int) in Functions has been deprecated | NpcInstance result = Functions.spawn(randomOffset > 50 ? Location.findPointToStay(loc, 0, randomOffset, ReflectionManager.DEFAULT.getGeoIndex()) : loc, npcId); ; ^

## java\l2\gameserver\model\SimpleSpawner.java (2)
- [unchecked] L34: unchecked conversion | _spawned = new ArrayList(1); ; ^ ; required: List<NpcInstance> ; found:    ArrayList
- [unchecked] L45: unchecked conversion | _spawned = new ArrayList(1); ; ^ ; required: List<NpcInstance> ; found:    ArrayList

## java\l2\gameserver\model\Skill.java (8)
- [deprecation] L294: isNumber(String) in NumberUtils has been deprecated | _element = NumberUtils.isNumber(set.getString("element", "NONE")) ? Element.getElementById(set.getInteger("element", -1)) : Element.getElementByName(set.getString("element", "none").toUpperCase()); ; ^
- [deprecation] L454: Msg in l2.gameserver.cache has been deprecated | activeChar.sendPacket(Msg.NOT_ENOUGH_MP); ; ^
- [deprecation] L460: Msg in l2.gameserver.cache has been deprecated | activeChar.sendPacket(Msg.NOT_ENOUGH_HP); ; ^
- [deprecation] L469: Msg in l2.gameserver.cache has been deprecated | activeChar.sendPacket(Msg.THERE_IS_NOT_ENOUGHT_SOUL); ; ^
- [deprecation] L485: Msg in l2.gameserver.cache has been deprecated | activeChar.sendPacket(Msg.OBSERVERS_CANNOT_PARTICIPATE); ; ^
- [deprecation] L507: Msg in l2.gameserver.cache has been deprecated | player.sendPacket(Msg.ONLY_FISHING_SKILLS_ARE_AVAILABLE); ; ^
- [deprecation] L518: Msg in l2.gameserver.cache has been deprecated | activeChar.getPlayer().sendPacket(Msg.YOUR_TARGET_IS_OUT_OF_RANGE); ; ^
- [deprecation] L523: Msg in l2.gameserver.cache has been deprecated | activeChar.getPlayer().sendPacket(Msg.THERE_IS_NOT_ENOUGH_SPACE_TO_MOVE_THE_SKILL_CANNOT_BE_USED); ; ^

## java\l2\gameserver\network\l2\GameClient.java (1)
- [deprecation] L198: Msg in l2.gameserver.cache has been deprecated | oldPlayer.sendPacket(Msg.ANOTHER_PERSON_HAS_LOGGED_IN_WITH_THE_SAME_ACCOUNT); ; ^

## java\l2\gameserver\network\l2\s2c\SystemMessage.java (2)
- [removal] L3119: Integer(int) in Integer has been deprecated and marked for removal | args.add(new Arg(11, new Integer(id))); ; ^
- [removal] L3125: Integer(int) in Integer has been deprecated and marked for removal | args.add(new Arg(2, new Integer(1000000 + id))); ; ^

## java\l2\gameserver\templates\item\ItemTemplate.java (1)
- [unchecked] L687: unchecked conversion | _enchantOptions = new HashIntObjectMap(); ; ^ ; required: IntObjectMap<int[]> ; found:    HashIntObjectMap

## java\l2\gameserver\templates\npc\NpcTemplate.java (8)
- [unchecked] L34: unchecked cast | public static final Constructor<NpcInstance> DEFAULT_TYPE_CONSTRUCTOR = (Constructor<NpcInstance>) NpcInstance.class.getConstructors()[0]; ; ^ ; required: Constructor<NpcInstance> ; found:    Constructor<?>
- [unchecked] L35: unchecked cast | public static final Constructor<CharacterAI> DEFAULT_AI_CONSTRUCTOR = (Constructor<CharacterAI>) CharacterAI.class.getConstructors()[0]; ; ^ ; required: Constructor<CharacterAI> ; found:    Constructor<?>
- [unchecked] L157: unchecked conversion | _classType = classType; ; ^ ; required: Class<NpcInstance> ; found:    Class
- [unchecked] L158: unchecked cast | _constructorType = (Constructor<NpcInstance>) _classType.getConstructors()[0]; ; ^ ; required: Constructor<NpcInstance> ; found:    Constructor<?>
- [unchecked] L184: unchecked conversion | _classAI = classAI; ; ^ ; required: Class<CharacterAI> ; found:    Class
- [unchecked] L185: unchecked cast | _constructorAI = (Constructor<CharacterAI>) _classAI.getConstructors()[0]; ; ^ ; required: Constructor<CharacterAI> ; found:    Constructor<?>
- [unchecked] L216: unchecked conversion | _teleportList = new TIntObjectHashMap(1); ; ^ ; required: TIntObjectHashMap<TeleportLocation[]> ; found:    TIntObjectHashMap
- [unchecked] L282: unchecked conversion | _skills = new TIntObjectHashMap(); ; ^ ; required: TIntObjectHashMap<Skill> ; found:    TIntObjectHashMap

## scripts\achievements\AchievementCondition.java (3)
- [unchecked] L32: unchecked call to <A>getAnnotation(Class<A>) as a member of the raw type Class | AchievementCondition.AchievementConditionName conditionName = (AchievementCondition.AchievementConditionName) clazz.getAnnotation(AchievementCondition.AchievementConditionName.class); ; ^ ; where A is a type-variable: ; A extends Annotation declared in method <A>getAnnotation(Class<A>)
- [unchecked] L35: unchecked call to getConstructor(Class<?>...) as a member of the raw type Class | Constructor<? extends AchievementCondition> ctor = clazz.getConstructor(String.class); ; ^ ; where T is a type-variable: ; T extends Object declared in class Class
- [unchecked] L35: unchecked conversion | Constructor<? extends AchievementCondition> ctor = clazz.getConstructor(String.class); ; ^ ; required: Constructor<? extends AchievementCondition> ; found:    Constructor

## scripts\achievements\AchievementMetricListeners.java (10)
- [unchecked] L66: unchecked conversion | CharListenerList.addGlobal(listener); ; ^ ; required: Listener<Creature> ; found:    Listener
- [unchecked] L66: unchecked method invocation: method addGlobal in class CharListenerList is applied to given types | CharListenerList.addGlobal(listener); ; ^ ; required: Listener<Creature> ; found:    Listener
- [unchecked] L71: unchecked conversion | CharListenerList.addGlobal(listener); ; ^ ; required: Listener<Creature> ; found:    Listener
- [unchecked] L71: unchecked method invocation: method addGlobal in class CharListenerList is applied to given types | CharListenerList.addGlobal(listener); ; ^ ; required: Listener<Creature> ; found:    Listener
- [unchecked] L84: unchecked conversion | CharListenerList.removeGlobal(listener); ; ^ ; required: Listener<Creature> ; found:    Listener
- [unchecked] L84: unchecked method invocation: method removeGlobal in class CharListenerList is applied to given types | CharListenerList.removeGlobal(listener); ; ^ ; required: Listener<Creature> ; found:    Listener
- [unchecked] L89: unchecked conversion | CharListenerList.removeGlobal(listener); ; ^ ; required: Listener<Creature> ; found:    Listener
- [unchecked] L89: unchecked method invocation: method removeGlobal in class CharListenerList is applied to given types | CharListenerList.removeGlobal(listener); ; ^ ; required: Listener<Creature> ; found:    Listener
- [unchecked] L160: unchecked call to ArrayList(Collection<? extends E>) as a member of the raw type ArrayList | ArrayList<Creature> raidParticipants = new ArrayList(raidBoss.getAggroList().getCharMap().keySet()); ; ^ ; where E is a type-variable: ; E extends Object declared in class ArrayList
- [unchecked] L160: unchecked conversion | ArrayList<Creature> raidParticipants = new ArrayList(raidBoss.getAggroList().getCharMap().keySet()); ; ^ ; required: ArrayList<Creature> ; found:    ArrayList

## scripts\achievements\AchievementUI.java (6)
- [unchecked] L95: unchecked conversion | achievementsHtml.append(buildAchievementHtml(activeCategoryIdx, activePageIdx, activeAchId, activeAchLvl, player, achievementInfo, (Pair) achievementInfoLevelsIdxLim.getRight(), invColor)); ; ^ ; required: Pair<Integer,Integer> ; found:    Pair
- [unchecked] L95: unchecked method invocation: method buildAchievementHtml in class AchievementUI is applied to given types | achievementsHtml.append(buildAchievementHtml(activeCategoryIdx, activePageIdx, activeAchId, activeAchLvl, player, achievementInfo, (Pair) achievementInfoLevelsIdxLim.getRight(), invColor)); ; ^ ; required: int,int,int,int,Player,AchievementInfo,Pair<Integer,Integer>,boolean ; found:    int,int,int,int,Player,AchievementInfo,Pair,boolean
- [unchecked] L333: unchecked conversion | items.add((Pair) Pair.of(item, (Object) length)); ; ^ ; required: E ; found:    Pair ; where E is a type-variable: ; E extends Object declared in interface List
- [unchecked] L333: unchecked method invocation: method add in interface List is applied to given types | items.add((Pair) Pair.of(item, (Object) length)); ; ^ ; required: E ; found:    Pair ; where E is a type-variable: ; E extends Object declared in interface List
- [unchecked] L358: unchecked conversion | result.add((Pair) Pair.of((Object) itemAndLength.getLeft(), (Object) Pair.of((Object) Math.max(begin - offset, 0), (Object) Math.min(length, end - offset)))); ; ^ ; required: E ; found:    Pair ; where E is a type-variable: ; E extends Object declared in class LinkedList
- [unchecked] L358: unchecked method invocation: method add in class LinkedList is applied to given types | result.add((Pair) Pair.of((Object) itemAndLength.getLeft(), (Object) Pair.of((Object) Math.max(begin - offset, 0), (Object) Math.min(length, end - offset)))); ; ^ ; required: E ; found:    Pair ; where E is a type-variable: ; E extends Object declared in class LinkedList

## scripts\ai\Antharas.java (44)
- [unchecked] L133: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_curse); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L133: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_curse); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L134: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_paralyze); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L134: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_paralyze); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L135: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_meteor); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L135: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_meteor); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L140: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_curse); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L140: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_curse); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L141: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_paralyze); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L141: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_paralyze); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L142: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_meteor); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L142: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_meteor); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L143: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_fear2); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L143: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_fear2); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L148: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_curse); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L148: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_curse); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L149: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_paralyze); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L149: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_paralyze); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L150: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_meteor); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L150: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_meteor); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L151: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_fear2); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L151: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_fear2); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L152: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_shock2); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L152: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_shock2); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L153: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_breath); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L153: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_breath); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L158: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_curse); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L158: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_curse); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L159: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_paralyze); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L159: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_paralyze); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L160: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_meteor); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L160: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_meteor); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L161: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_fear2); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L161: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_fear2); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L162: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_shock2); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L162: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_shock2); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L163: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_fear); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L163: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_fear); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L164: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_shock); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L164: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_shock); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L165: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_breath); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L165: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_breath); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L169: unchecked conversion | Skill r_skill = selectTopSkill(d_skill); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L169: unchecked method invocation: method selectTopSkill in class DefaultAI is applied to given types | Skill r_skill = selectTopSkill(d_skill); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap

## scripts\ai\Baium.java (10)
- [unchecked] L101: unchecked conversion | addDesiredSkill(d_skill, target, distance, energy_wave); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L101: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, energy_wave); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L102: unchecked conversion | addDesiredSkill(d_skill, target, distance, earth_quake); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L102: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, earth_quake); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L105: unchecked conversion | addDesiredSkill(d_skill, target, distance, group_hold); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L105: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, group_hold); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L109: unchecked conversion | addDesiredSkill(d_skill, target, distance, thunderbolt); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L109: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, thunderbolt); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L111: unchecked conversion | r_skill = selectTopSkill(d_skill); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L111: unchecked method invocation: method selectTopSkill in class DefaultAI is applied to given types | r_skill = selectTopSkill(d_skill); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap

## scripts\ai\Kama56Boss.java (1)
- [unchecked] L65: unchecked call to add(E) as a member of the raw type LazyArrayList | alive.add(p); ; ^ ; where E is a type-variable: ; E extends Object declared in class LazyArrayList

## scripts\ai\Valakas.java (26)
- [unchecked] L153: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_breath_low); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L153: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_breath_low); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L154: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_tail_stomp_a); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L154: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_tail_stomp_a); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L155: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_meteor); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L155: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_meteor); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L156: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_fear); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L156: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_fear); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L162: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_breath_low); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L162: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_breath_low); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L163: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_tail_stomp_a); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L163: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_tail_stomp_a); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L164: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_breath_high); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L164: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_breath_high); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L165: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_tail_lash); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L165: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_tail_lash); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L166: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_meteor); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L166: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_meteor); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L167: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_fear); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L167: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_fear); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L172: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_breath_low); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L172: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_breath_low); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L173: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_tail_stomp_a); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L173: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_tail_stomp_a); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill
- [unchecked] L174: unchecked conversion | addDesiredSkill(d_skill, target, distance, s_breath_high); ; ^ ; required: Map<Skill,Integer> ; found:    HashMap
- [unchecked] L174: unchecked method invocation: method addDesiredSkill in class DefaultAI is applied to given types | addDesiredSkill(d_skill, target, distance, s_breath_high); ; ^ ; required: Map<Skill,Integer>,Creature,double,Skill ; found:    HashMap,Creature,double,Skill

