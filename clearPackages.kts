import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

// Replace these with your own values
val owner = "nekzabirov"
val repo = "Firebase_KMM"
val token = "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"

// Function to delete a package by its ID
fun deletePackage(packageId: String) {
    val deleteUrl = "https://api.github.com/users/packages/$packageId"

    val url = URL(deleteUrl)
    val connection = url.openConnection() as HttpURLConnection
    connection.requestMethod = "DELETE"
    connection.setRequestProperty("Authorization", "Bearer $token")

    val responseCode = connection.responseCode

    if (responseCode == 204) {
        println("Package $packageId deleted successfully.")
    } else {
        println("Failed to delete package $packageId. Status code: $responseCode")
    }

    connection.disconnect()
}

// Get a list of all packages in the repository
val packagesUrl = "https://api.github.com/user/packages?package_type=maven"

val url = URL(packagesUrl)
val connection = url.openConnection() as HttpURLConnection
connection.requestMethod = "GET"
connection.setRequestProperty("Authorization", "Bearer $token")
connection.setRequestProperty("Accept", "application/vnd.github.package-deletes-preview+json")

val responseCode = connection.responseCode

if (responseCode == 200) {
    val reader = BufferedReader(InputStreamReader(connection.inputStream))
    val packagesJson = reader.readText()


    // Extract and delete packages
    val packageRegex = "\"id\": (\\d+),".toRegex()
    val packageIds = packageRegex.findAll(packagesJson).map { it.groupValues[1] }

    for (packageId in packageIds) {
        println("To delete $packageId")
        deletePackage(packageId)
    }

    reader.close()
} else {
    println("Failed to fetch package list. Status code: $responseCode")
}

connection.disconnect()
