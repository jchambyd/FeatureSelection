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
public class AttributeInfo {

	private float value;
	private long count;
	private ClassInfo down;
	private AttributeInfo next;
	
	public AttributeInfo(float value, long count, ClassInfo down, AttributeInfo next)
	{
		this.value = value;
		this.count = count;
		this.down = down;
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
	 * @return the down
	 */
	public ClassInfo getDown() {
		return down;
	}

	/**
	 * @param down the down to set
	 */
	public void setDown(ClassInfo down) {
		this.down = down;
	}

	/**
	 * @return the next
	 */
	public AttributeInfo getNext() {
		return next;
	}

	/**
	 * @param next the next to set
	 */
	public void setNext(AttributeInfo next) {
		this.next = next;
	}
}
