package xyz.tneubauer.test;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class TestCases {

	@Test
	public void emptyUnion() {
		DoubleIntUnion union = new DoubleIntUnion();

		assertTrue(union.isEmpty());
		assertFalse(union.hasValue());
		assertFalse(union.isDouble());
		assertFalse(union.isInt());


		assertThrows(Exception.class, union::getDouble);

		assertThrows(Exception.class, union::getInt);

	}

	@Test
	public void doubleUnionBase() {
		DoubleIntUnion union = new DoubleIntUnion(0.0);

		assertFalse(union.isEmpty());
		assertTrue(union.hasValue());

		assertTrue(union.isDouble());
		assertFalse(union.isInt());

		assertEquals(0.0, union.getDouble());

		assertThrows(Exception.class, union::getInt);

	}

	@Test
	public void doubleUnionValues() {
		Random random = new Random();
		int i = 0;
		while (i < 100000) {
			double value = Double.longBitsToDouble(random.nextLong());
			if (Double.isInfinite(value) || Double.isNaN(value)) continue;
			if (value < 0) value = -value;
			DoubleIntUnion union = new DoubleIntUnion(value);

			assertEquals(value, union.getDouble());
			i++;
		}
	}

	@Test
	public void intUnionBase() {
		DoubleIntUnion union = new DoubleIntUnion((int) 0);

		assertFalse(union.isEmpty());
		assertTrue(union.hasValue());

		assertFalse(union.isDouble());
		assertTrue(union.isInt());

		assertEquals(0, union.getInt());

		assertThrows(Exception.class, union::getDouble);

	}

	@Test
	public void intUnionValues() {
		Random random = new Random();
		int i = 0;
		while (i < 100000) {
			int value = random.nextInt();
			DoubleIntUnion union = new DoubleIntUnion(value);

			assertEquals(value, union.getInt());
			i++;
		}

	}
}
