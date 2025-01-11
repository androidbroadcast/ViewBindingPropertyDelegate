package dev.androidbroadcast.vbpd.sample

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    val name: String,
    val surname: String,
    val email: String,
): Parcelable

val personsStub = listOf(
    Person("John", "Doe", "john.doe@example.com"),
    Person("Jane", "Smith", "jane.smith@example.com"),
    Person("Robert", "Johnson", "robert.johnson@example.com"),
    Person("Emily", "Davis", "emily.davis@example.com"),
    Person("Michael", "Wilson", "michael.wilson@example.com"),
    Person("Sarah", "Taylor", "sarah.taylor@example.com"),
    Person("William", "Brown", "william.brown@example.com"),
    Person("Olivia", "White", "olivia.white@example.com"),
    Person("James", "Martin", "james.martin@example.com"),
    Person("Sophia", "Anderson", "sophia.anderson@example.com"),
    Person("Benjamin", "Harris", "benjamin.harris@example.com"),
    Person("Emma", "Clark", "emma.clark@example.com"),
    Person("Lucas", "Lewis", "lucas.lewis@example.com"),
    Person("Mia", "Walker", "mia.walker@example.com"),
    Person("Alexander", "Young", "alexander.young@example.com"),
    Person("Isabella", "King", "isabella.king@example.com"),
    Person("Daniel", "Scott", "daniel.scott@example.com"),
    Person("Ava", "Hall", "ava.hall@example.com"),
    Person("Matthew", "Green", "matthew.green@example.com"),
    Person("Charlotte", "Adams", "charlotte.adams@example.com")
)
