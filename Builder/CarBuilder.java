interface CarBuilder {
	CarBuilder setWheels(final int wheels);
	CarBuilder setColor(final String color);
	Car build();
}
