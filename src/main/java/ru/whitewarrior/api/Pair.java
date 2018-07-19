package ru.whitewarrior.api;

public final class Pair<K, V> {
	private K key;
	private V value;

	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}
	
	@Override
	public int hashCode() {
		return key.hashCode() + value.hashCode() * 31;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Pair && ((Pair) obj).getKey().equals(getKey()) && ((Pair) obj).getValue().equals(getValue());
	}
}
