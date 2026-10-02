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
fun EditExperimentScreen(
    experiment: Experiment,
    onExperimentUpdated: (Experiment) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    // Store the existing experiment information in editable fields
    var name by remember {
        mutableStateOf(experiment.name)
    }

    var description by remember {
        mutableStateOf(experiment.description)
    }

    var date by remember {
        mutableStateOf(experiment.date)
    }

    var temperature by remember {
        mutableStateOf(experiment.temperature.toString())
    }

    var pH by remember {
        mutableStateOf(experiment.pH.toString())
    }

    var treatmentTime by remember {
        mutableStateOf(experiment.treatmentTime.toString())
    }

    // Convert the results list into text so it can be edited
    var results by remember {
        mutableStateOf(experiment.results.joinToString(", "))
    }

    // Stores an error message if the entered information is invalid
    var errorMessage by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),

        // Adds space between the elements
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // Button to return to the previous screen
        OutlinedButton(
            onClick = onBack
        ) {
            Text("← Back")
        }

        // Screen title
        Text(
            text = "Edit Experiment",
            style = MaterialTheme.typography.headlineMedium
        )

        // Instructions for the user
        Text(
            text = "Update the experiment information below.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // Field for editing the experiment name
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

        // Field for editing the description
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

        // Field for editing the date
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

        // Field for editing the temperature
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

        // Field for editing the pH value
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

        // Field for editing the treatment time
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

        // Field for editing the experiment results
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

        // Display an error message when the information is invalid
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

        // Save button validates the data and updates the experiment
        Button(
            onClick = {

                // Convert the text fields into numbers
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

                    // Check that the pH is between 0 and 14
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

                        // Here we create an updated experiment using the new values
                        val updatedExperiment = Experiment(
                            // Keep the original ID so the correct experiment is updated
                            id = experiment.id,
                            name = name.trim(),
                            description = description.trim(),
                            date = date.trim(),
                            temperature = temperatureValue,
                            pH = pHValue,
                            treatmentTime = treatmentTimeValue,

                            // Convert the comma-separated text back into a list
                            results = results
                                .split(",")
                                .map { it.trim() }
                                .filter { it.isNotEmpty() }
                        )

                        // Send the updated experiment to the parent screen
                        onExperimentUpdated(updatedExperiment)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Changes")
        }
    }
}
