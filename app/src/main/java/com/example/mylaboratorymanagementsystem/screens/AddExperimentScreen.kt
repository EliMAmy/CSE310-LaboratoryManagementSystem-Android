package com.example.mylaboratorymanagementsystem.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mylaboratorymanagementsystem.model.Experiment

@Composable
fun AddExperimentScreen(
    onExperimentAdded: (Experiment) -> Unit,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {

    // Store the information entered by the user
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf("") }
    var pH by remember { mutableStateOf("") }
    var treatmentTime by remember { mutableStateOf("") }
    var results by remember { mutableStateOf("") }

    // Stores an error message when the user enters invalid information
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),

        // Adds space between the elements on the screen
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // Button to return to the previous screen
        OutlinedButton(
            onClick = onBack
        ) {
            Text("← Back")
        }

        // Title of the screen
        Text(
            text = "Add Experiment",
            style = MaterialTheme.typography.headlineMedium
        )

        // Short instruction for the user
        Text(
            text = "Enter the experiment information below.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // Text field for the experiment name
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                errorMessage = ""
            },
            label = {
                Text("Experiment Name")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Text field for the experiment description
        OutlinedTextField(
            value = description,
            onValueChange = {
                description = it
                errorMessage = ""
            },
            label = {
                Text("Description")
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Text field for the experiment date
        OutlinedTextField(
            value = date,
            onValueChange = {
                date = it
                errorMessage = ""
            },
            label = {
                Text("Date")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Text field for temperature
        OutlinedTextField(
            value = temperature,
            onValueChange = {
                temperature = it
                errorMessage = ""
            },
            label = {
                Text("Temperature (°C)")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Text field for pH value
        OutlinedTextField(
            value = pH,
            onValueChange = {
                pH = it
                errorMessage = ""
            },
            label = {
                Text("pH")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Text field for treatment time
        OutlinedTextField(
            value = treatmentTime,
            onValueChange = {
                treatmentTime = it
                errorMessage = ""
            },
            label = {
                Text("Treatment Time (hours)")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Text field for the experiment results
        OutlinedTextField(
            value = results,
            onValueChange = {
                results = it
                errorMessage = ""
            },
            label = {
                Text("Results (comma-separated)")
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Show an error message if the entered information is invalid
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // Button that validates the information and creates the experiment
        Button(
            onClick = {

                // Convert text values into numbers
                val temperatureValue =
                    temperature.toDoubleOrNull()

                val pHValue =
                    pH.toDoubleOrNull()

                val treatmentTimeValue =
                    treatmentTime.toDoubleOrNull()

                // Check that all required information is valid
                when {

                    name.isBlank() -> {
                        errorMessage =
                            "Please enter an experiment name."
                    }

                    description.isBlank() -> {
                        errorMessage =
                            "Please enter a description."
                    }

                    date.isBlank() -> {
                        errorMessage =
                            "Please enter a date."
                    }

                    temperatureValue == null -> {
                        errorMessage =
                            "Please enter a valid temperature."
                    }

                    pHValue == null -> {
                        errorMessage =
                            "Please enter a valid pH value."
                    }

                    // Make sure the pH is within the valid range
                    pHValue !in 0.0..14.0 -> {
                        errorMessage =
                            "pH must be between 0 and 14."
                    }

                    treatmentTimeValue == null -> {
                        errorMessage =
                            "Please enter a valid treatment time."
                    }

                    results.isBlank() -> {
                        errorMessage =
                            "Please enter the experiment results."
                    }

                    else -> {

                        // Create a new Experiment using the entered information
                        val experiment = Experiment(
                            name = name.trim(),
                            description = description.trim(),
                            date = date.trim(),
                            temperature = temperatureValue,
                            pH = pHValue,
                            treatmentTime = treatmentTimeValue,

                            // Convert comma-separated results into a list
                            results = results
                                .split(",")
                                .map { it.trim() }
                                .filter { it.isNotEmpty() }
                        )

                        // Send the new experiment to the parent screen
                        onExperimentAdded(experiment)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Experiment")
        }
    }
}
