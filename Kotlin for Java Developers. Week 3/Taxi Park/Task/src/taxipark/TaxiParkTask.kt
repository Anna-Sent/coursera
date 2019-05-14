package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        allDrivers.associate { driver -> driver to findAllTrips(driver) }
                .filter { (_, trips) -> trips.isEmpty() }
                .map { (driver, _) -> driver }
                .toSet()

fun TaxiPark.findAllTrips(driver: Driver): List<Trip> {
    return trips.filter { trip -> trip.driver == driver }
}

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        allPassengers.associate { passenger -> passenger to findAllTrips(passenger) }
                .filter { (_, trips) -> trips.size >= minTrips }
                .map { (passenger, _) -> passenger }
                .toSet()

fun TaxiPark.findAllTrips(passenger: Passenger): List<Trip> {
    return trips.filter { trip -> passenger in trip.passengers }
}

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        allPassengers.associate { passenger -> passenger to findAllTrips(passenger) }
                .filter { (_, trips) -> trips.count { trip -> trip.driver == driver } > 1 }
                .map { (passenger, _) -> passenger }
                .toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        allPassengers.associate { passenger -> passenger to findAllTrips(passenger) }
                .filter { (_, trips) ->
                    val (tripsWithDiscount, tripsNoDiscount) = trips.partition { trip -> trip.discount != null }
                    tripsWithDiscount.size > tripsNoDiscount.size
                }
                .map { (passenger, _) -> passenger }
                .toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    if (trips.isEmpty()) return null
    val durations = trips.map { trip -> trip.duration }
            .sortedBy { it }
    val min = durations.min()!! / 10 * 10
    val max = durations.max()!! / 10 * 10
    return generateSequence(min) { it + 10 }
            .takeWhile { it <= max }
            .associate { index -> IntRange(index, index + 9) to durations.filter { duration -> duration in index..index + 9 } }
            .toList()
            .sortedBy { (_, durations) -> durations.size }
            .map { (range, _) -> range }
            .last()
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    return trips.isNotEmpty() && findIncomeOf20PercentDrivers() / findTotalIncome() >= 0.8
}

fun TaxiPark.findIncomeOf20PercentDrivers(): Double {
    return allDrivers.map { driver -> findIncome(driver) }
            .sortedByDescending { it }
            .take((allDrivers.size * 0.2).toInt())
            .sumByDouble { it }
}

fun TaxiPark.findIncome(driver: Driver): Double {
    return trips.filter { trip -> trip.driver == driver }
            .sumByDouble { trip -> trip.cost }
}

fun TaxiPark.findTotalIncome(): Double {
    return trips.sumByDouble { trip -> trip.cost }
}
