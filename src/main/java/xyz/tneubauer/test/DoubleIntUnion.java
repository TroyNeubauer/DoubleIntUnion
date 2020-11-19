package xyz.tneubauer.test;

class DoubleIntUnion {
	private double value;

	public DoubleIntUnion() {
		setEmpty();
	}

	public DoubleIntUnion(double value) {
		setDouble(value);
	}

	public DoubleIntUnion(int value) {
		setInt(value);
	}

	public void setEmpty() {
		this.value = Double.NaN;
	}

	public void setInt(int newValue) {
		//Use this mask to keep the sign bit in place so we can get it later without it mangling the double's sign bit
		long extended = newValue & 0xFFFFFFFFL;
		double newDouble = Double.longBitsToDouble(extended);
		this.value = -newDouble;
	}

	public void setDouble(double newValue) {
		if (newValue < 0.0) throw new IllegalArgumentException("DoubleIntUnion can only store positive values! Not: " + newValue);
		this.value = newValue;
	}

	public boolean isEmpty() {
		return Double.isNaN(this.value);
	}

	public boolean hasValue() {
		return !isEmpty();
	}

	private static final long DOUBLE_ZERO = Double.doubleToLongBits(0.0);
	private static final long NEGITIVE_DOUBLE_ZERO = Double.doubleToLongBits(-0.0);

	public boolean isDouble() {
		//Use long comparisons because -0.0 == 0.0 and we need to distinguish the two
		return hasValue() && (this.value > 0 || Double.doubleToLongBits(this.value) == DOUBLE_ZERO);
	}

	public boolean isInt() {
		return hasValue() && (this.value < 0 || Double.doubleToLongBits(this.value) == NEGITIVE_DOUBLE_ZERO);
	}

	public double getDouble() {
		if (!isDouble()) throw new IllegalStateException("Cannot get double. Object is in state: " + this.toString());

		return this.value;
	}

	public int getInt() {
		if (!isInt()) throw new IllegalStateException("Cannot get int. Object is in state: " + this.toString());
		long result = Double.doubleToLongBits(-this.value);
		//Check 2^32 - 1 and not Integer.MAX_VALUE because the sign bit will make a <= Integer.MAX_VALUE check fail
		if (result > Math.pow(2, 32) - 1) throw new IllegalStateException("Int result out of range: " + result);
		return (int) result;
	}


	@Override
	public String toString() {
		if (isEmpty())
			return "{empty}";
		else if (isDouble())
			return String.valueOf(getDouble());
		else
			return String.valueOf(getInt());
	}
}

