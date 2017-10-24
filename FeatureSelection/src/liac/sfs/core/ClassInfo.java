/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liac.sfs.core;

/**
 *
 * @author liac01
 */
public class ClassInfo {
	private float value;
	private long count;
	private ClassInfo next;
	
	public ClassInfo(float value, long count, ClassInfo next)
	{
		this.value = value;
		this.count = count;
		this.next = next;		
	}

	/**
	 * @return the value
	 */
	public float getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(float value) {
		this.value = value;
	}

	/**
	 * @return the count
	 */
	public long getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(long count) {
		this.count = count;
	}

	/**
	 * @return the next
	 */
	public ClassInfo getNext() {
		return next;
	}

	/**
	 * @param next the next to set
	 */
	public void setNext(ClassInfo next) {
		this.next = next;
	}
	
}
