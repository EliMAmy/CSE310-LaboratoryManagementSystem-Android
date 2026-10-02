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

    var results by remember {
        mutableStateOf(experiment.results.joinToString(", "))
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),

        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // Back button
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

        Text(
            text = "Update the experiment information below.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // Experiment name
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

        // Description
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

        // Date
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

        // Temperature
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

        // pH
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

        // Treatment time
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

        // Results
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

        // Error message
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

        // Save button
        Button(
            onClick = {

                val temperatureValue =
                    temperature.toDoubleOrNull()

                val pHValue =
                    pH.toDoubleOrNull()

                val treatmentTimeValue =
                    treatmentTime.toDoubleOrNull()

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

                        val updatedExperiment = Experiment(
                            id = experiment.id,
                            name = name.trim(),
                            description = description.trim(),
                            date = date.trim(),
                            temperature = temperatureValue,
                            pH = pHValue,
                            treatmentTime = treatmentTimeValue,
                            results = results
                                .split(",")
                                .map { it.trim() }
                                .filter { it.isNotEmpty() }
                        )

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
