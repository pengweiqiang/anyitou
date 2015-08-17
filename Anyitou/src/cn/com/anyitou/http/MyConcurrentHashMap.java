package cn.com.anyitou.http;

import java.util.concurrent.ConcurrentHashMap;

public class MyConcurrentHashMap<K,V> extends ConcurrentHashMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8979465118755708713L;

	@Override
	public V put(K key, V value) {
		if(value==null){
			value = (V) String.valueOf("");
		}
		return super.put(key, value);
	}
	
	
	
}
