package com.wen.awenboot.test.es;

/**
 * 
 * @author wen
 * @date 2020年3月26日
 */
public class KeyValuePair<T, V> {
	private T key;
	private V value;

	public KeyValuePair() {
	}

	public KeyValuePair(T key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public static <T,V> KeyValuePair<T,V> build(T key, V value) {
		return new KeyValuePair<T, V>(key, value);
	}

	public T getKey() {
		return key;
	}

	public void setKey(T key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

}
