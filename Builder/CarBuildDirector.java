class CarBuildDirector {
	private CarBuilder builder;
	
	CarBuildDirector(final CarBuilder builder) {
		this.builder = builder;
	}

	Car construct() {
		return builder.setWheels(4).setColor("Red").build();
	}

	public static void main(final String[] args) {
		CarBuilder builder = new CarBuilderImpl();
		CarBuildDirector cbd = new CarBuildDirector(builder);
		System.out.println(cbd.construct());
	}
}
