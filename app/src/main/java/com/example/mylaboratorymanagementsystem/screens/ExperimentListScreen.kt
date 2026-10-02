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

        Text(
            text = "Manage your laboratory experiments",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // Back button
        OutlinedButton(
            onClick = onBack
        ) {
            Text("← Back")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        if (experiments.isEmpty()) {

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

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

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


@Composable
fun ExperimentCard(
    experiment: Experiment,
    onEditExperiment: (Experiment) -> Unit,
    onDeleteExperiment: (Experiment) -> Unit
) {

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // Experiment name
            Text(
                text = experiment.name,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            // Experiment information
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

            // Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Button(
                    onClick = {
                        onEditExperiment(experiment)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Edit")
                }

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

    // Delete confirmation dialog
    if (showDeleteDialog) {

        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
            },

            title = {
                Text("Delete Experiment")
            },

            text = {
                Text(
                    "Are you sure you want to delete " +
                            "\"${experiment.name}\"?"
                )
            },

            confirmButton = {
                TextButton(
                    onClick = {

                        onDeleteExperiment(experiment)

                        showDeleteDialog = false
                    }
                ) {
                    Text("Delete")
                }
            },

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
