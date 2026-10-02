package com.example.mylaboratorymanagementsystem.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun SearchExperimentScreen(
    experiments: List<Experiment>,
    onEditExperiment: (Experiment) -> Unit,
    onDeleteExperiment: (Experiment) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    var searchText by remember {
        mutableStateOf("")
    }

    val filteredExperiments = experiments.filter {
        it.name.contains(
            searchText,
            ignoreCase = true
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Screen title
        Text(
            text = "Search Experiments",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Find an experiment by name",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // Search field
        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
            },
            label = {
                Text("Experiment Name")
            },
            placeholder = {
                Text("Enter experiment name")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // Results information
        if (searchText.isNotBlank()) {

            Text(
                text = "${filteredExperiments.size} experiment(s) found",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }

        if (filteredExperiments.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = if (searchText.isBlank()) {
                        "No experiments available."
                    } else {
                        "No experiments found."
                    },
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = if (searchText.isBlank()) {
                        "Add an experiment first."
                    } else {
                        "Try searching with another name."
                    }
                )
            }

        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(filteredExperiments) { experiment ->

                    ExperimentCard(
                        experiment = experiment,
                        onEditExperiment = onEditExperiment,
                        onDeleteExperiment = onDeleteExperiment
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        // Back button
        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("← Back")
        }
    }
}
