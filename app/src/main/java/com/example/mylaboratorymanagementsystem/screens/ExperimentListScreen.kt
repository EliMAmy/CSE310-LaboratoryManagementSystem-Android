package com.example.mylaboratorymanagementsystem.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mylaboratorymanagementsystem.model.Experiment


// Displays the list of all laboratory experiments
@Composable
fun ExperimentListScreen(
    experiments: List<Experiment>,
    onEditExperiment: (Experiment) -> Unit,
    onDeleteExperiment: (Experiment) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Screen title
        Text(
            text = "Experiments",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // Short description of the screen
        Text(
            text = "Manage your laboratory experiments",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // Button to return to the previous screen
        OutlinedButton(
            onClick = onBack
        ) {
            Text("← Back")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // Check if there are any experiments
        if (experiments.isEmpty()) {

            // Message shown when there are no experiments
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "No experiments found.",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Add an experiment to see it here."
                )
            }

        } else {

            // LazyColumn displays the experiments in a scrollable list
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // Create one card for each experiment
                items(experiments) { experiment ->

                    ExperimentCard(
                        experiment = experiment,
                        onEditExperiment = onEditExperiment,
                        onDeleteExperiment = onDeleteExperiment
                    )
                }
            }
        }
    }
}


// Displays the information and buttons for one experiment
@Composable
fun ExperimentCard(
    experiment: Experiment,
    onEditExperiment: (Experiment) -> Unit,
    onDeleteExperiment: (Experiment) -> Unit
) {

    // Controls whether the delete confirmation dialog is visible
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    // Card contains all the information for one experiment
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // Display the experiment name
            Text(
                text = experiment.name,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            // Display the experiment information
            Text(
                text = "Description: ${experiment.description}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Date: ${experiment.date}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Temperature: ${experiment.temperature} °C",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "pH: ${experiment.pH}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Treatment Time: ${experiment.treatmentTime} hours",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Results: ${experiment.results.joinToString(", ")}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // Row contains the Edit and Delete buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                // Edit button sends the selected experiment
                // to the edit screen
                Button(
                    onClick = {
                        onEditExperiment(experiment)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Edit")
                }

                // Delete button opens a confirmation dialog
                OutlinedButton(
                    onClick = {
                        showDeleteDialog = true
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Delete")
                }
            }
        }
    }

    // Show the confirmation dialog when the user clicks Delete
    if (showDeleteDialog) {

        AlertDialog(
            // Close the dialog when the user dismisses it
            onDismissRequest = {
                showDeleteDialog = false
            },

            // Dialog title
            title = {
                Text("Delete Experiment")
            },

            // Ask the user to confirm the deletion
            text = {
                Text(
                    "Are you sure you want to delete " +
                            "\"${experiment.name}\"?"
                )
            },

            // Delete button inside the dialog
            confirmButton = {
                TextButton(
                    onClick = {

                        // Delete the selected experiment
                        onDeleteExperiment(experiment)

                        // Close the dialog
                        showDeleteDialog = false
                    }
                ) {
                    Text("Delete")
                }
            },

            // Cancel button inside the dialog
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
