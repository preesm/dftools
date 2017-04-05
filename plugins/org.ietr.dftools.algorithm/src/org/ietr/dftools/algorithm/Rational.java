/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
 * Maxime Pelcat <maxime.pelcat@insa-rennes.fr> (2011)
 *
 * This software is a computer program whose purpose is to [describe
 * functionalities and technical features of your software].
 *
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability. 
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and,  more generally, to use and operate it in the 
 * same conditions as regards security. 
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *******************************************************************************/
package org.ietr.dftools.algorithm;

import java.util.List;
import java.util.Vector;

/**
 * Class to represent rational numbers, and performs computation on it.
 * 
 * @author jpiat
 * 
 */
public class Rational {

	/**
	 * Adds two rationnal a, b and returns the sum
	 * 
	 * @param a
	 * @param b
	 * @return the rational containing the sum of a and b
	 */
	public static Rational add(Rational a, Rational b) {
		if (a.zero()) {
			return new Rational(b.num, b.denum);
		} else if (b.zero()) {
			return new Rational(a.num, a.denum);
		}
		int denumCom = SDFMath.lcm(a.denum, b.denum);
		int num = (a.num * (denumCom / a.denum))
				+ (b.num * (denumCom / b.denum));
		Rational res = new Rational(num, denumCom);
		return res;
	}

	/**
	 * Divides two rational a and b
	 * 
	 * @param a
	 * @param b
	 * @return the result of the division of a by b
	 */
	public static Rational div(Rational a, Rational b) {
		Rational newB = new Rational(b.denum, b.num);
		return prod(a, newB);
	}

	/**
	 * Gives the greater common divider of an integer and a rationnal
	 * 
	 * @param a
	 * @param b
	 * @return the gcd of a and b
	 */
	public static int gcd(int a, Rational b) {
		return SDFMath.lcm(a, b.denum);
	}

	/**
	 * Gives the greater common divider of s a set of rational
	 * 
	 * @param fracs
	 * @return the gcd of the given set of rational
	 */
	public static int gcd(Iterable<Rational> fracs) {
		int gcd = 1;
		for (Rational f : fracs) {
			gcd = gcd(gcd, f.abs());
		}
		return gcd;
	}

	/**
	 * Gives the greater common divider of two rational a and b
	 * 
	 * @param a
	 * @param b
	 * @return the gcd of a and b
	 */
	public static int gcd(Rational a, Rational b) {
		return SDFMath.lcm(a.denum, b.denum);
	}

	/**
	 * Gives the product of two rational a and b
	 * 
	 * @param a
	 * @param b
	 * @return the product of a and b
	 */
	public static Rational prod(Rational a, Rational b) {
		Rational res = new Rational(a.num * b.num, a.denum * b.denum);
		if (res.zero()) {
			return new Rational(0, 1);
		}
		res.reduc();
		return res;
	}

	/**
	 * Substracts two rational and returns the sub
	 * 
	 * @param a
	 * @param b
	 * @return a less b
	 */
	public static Rational sub(Rational a, Rational b) {
		int denumCom = SDFMath.lcm(a.denum, b.denum);
		int num = (a.num * (denumCom / a.denum))
				- (b.num * (denumCom / b.denum));
		Rational res = new Rational(num, denumCom);
		return res;
	}

	/**
	 * Transforms a set of rationals into integers
	 * 
	 * @param fracs
	 * @return the natural representation of a set of rational
	 */
	public static List<Integer> toNatural(Iterable<Rational> fracs) {
		long gcd = new Long(gcd(fracs));
		Vector<Integer> result = new Vector<Integer>();
		for (Rational f : fracs) {
			Rational absRat = f.abs();
			long longNum = new Long(absRat.num);
			long longRes = (longNum*gcd)/new Long(absRat.denum) ;
			result.add(((Long)longRes).intValue());
		}
		return result;
	}

	private int denum;

	private int num;

	/**
	 * Construct a new zero rational
	 */
	public Rational() {
		this.num = 0;
		this.denum = 0;
	}

	/**
	 * Coinstructs a new rationan given its numerator and denumerator
	 * 
	 * @param num
	 * @param denum
	 */
	public Rational(int num, int denum) {
		this.num = num;
		this.denum = denum;
	}

	/**
	 * Gives the absolute value of the rational
	 * 
	 * @return the the absolute value of this rational
	 */
	public Rational abs() {
		return new Rational(Math.abs(num), Math.abs(denum));
	}

	@Override
	public Rational clone() {
		return new Rational(num, denum);
	}

	/**
	 * Gives the double value of this rational
	 * 
	 * @return the double value of this rational
	 */
	public double doubleValue() {
		double doubleNum = new Double(num);
		double doubleDenum = new Double(denum);
		return doubleNum / doubleDenum;
	}

	/**
	 * Gives the rational deumerator
	 * 
	 * @return the denuminator
	 */
	public int getDenum() {
		return denum;
	}

	/**
	 * Gives the rational numerator
	 * 
	 * @return the numerator
	 */
	public int getNum() {
		return num;
	}

	/**
	 * Gives whether or not this rational is positive
	 * 
	 * @return True if this rational is positive
	 */
	public boolean greaterThanZero() {
		if ((num >= 0 && denum >= 0) || (num <= 0 && denum <= 0)) {
			return true;
		}
		return false;
	}

	private void reduc() {
		int pgcd = SDFMath.gcd(Math.abs(num), Math.abs(denum));
		num = num / pgcd;
		denum = denum / pgcd;
	}

	@Override
	public String toString() {
		return num + "/" + denum;
	}

	/**
	 * Returns true if this rational is equal to zero
	 * 
	 * @return true if equal to zero
	 */
	public boolean zero() {
		return (num == 0 || denum == 0);
	}
}
