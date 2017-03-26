import java.util.*;

class PooledObjectPool {
	private static Long expTime = 6000; //6 seconds
	static HashMap<PooledObject, Long> available = new HashMap<>();
	static HashMap<PooledObject, Long> inUse = new HashMap<>();

	synchronized static PooledObject getObject() {
		Long now = System.currentTimeMillis();
		if (available.isEmpty()) {
			PooledObject po = new PooledObject();
			push(inUse, po, now);
			return po;
		}
		else {
			for (Map.Entry<PooledObject, Long> entry : available.entrySet()) {
				if ((now - entry.getValue()) > expTime) popElement(available);
				else {
					PooledObject po = popElement(available, entry.getKey());
					cleanUp(po);
					push(inUse, po, now);
					return po;
				}
			}
		}
		return null;
	}

	private synchronized static void push(HashMap<PooledObject, Long> map, PooledObject po, Long now) {
		map.put(po, now);
	}

	static void releasedObject(PooledObject po) {
		cleanUp(po);
		available.put(po, System.currentTimeMillis());
		inUse.remove(po);
	}

	static PooledObject popElement(HashMap<PooledObject, Long> map) {
		Map.entry<PooledObject, Long> entry = map.entrySet().iterator().next();
		PooledObject key = entry.getKey();
		map.remove(entry.getKey());
		return key;
	}

	static PooledObject popElement(HashMap<PooledObject, Long> map, PooledObject key) {
		map.remove(key);
		return key;
	}

	static void cleanUp(PooledObject po) {
		po.setTemp1(null);
		po.setTemp2(null);
		po.setTemp3(null);
	}
}
