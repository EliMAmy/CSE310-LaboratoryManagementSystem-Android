package com.example.mylaboratorymanagementsystem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mylaboratorymanagementsystem.ui.theme.MyLaboratoryManagementSystemTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mylaboratorymanagementsystem.screens.AddExperimentScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylaboratorymanagementsystem.viewmodel.ExperimentViewModel
import com.example.mylaboratorymanagementsystem.screens.ExperimentListScreen
import com.example.mylaboratorymanagementsystem.screens.SearchExperimentScreen
import com.example.mylaboratorymanagementsystem.screens.EditExperimentScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.mylaboratorymanagementsystem.model.Experiment
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.graphics.Color


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Allows the app content to use the full screen
        enableEdgeToEdge()

        // Start the Jetpack Compose user interface
        setContent {

            // Create the ViewModel that manages the experiments
            val experimentViewModel: ExperimentViewModel = viewModel()

            // Stores the experiment currently selected for editing
            var selectedExperiment by remember {
                mutableStateOf<Experiment?>(null)
            }

            // Creates the controller used to move between screens
            val navController = rememberNavController()

            // Defines all the screens and their navigation routes
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {

                // Home screen
                composable("home") {
                    HomeScreen(
                        // Navigate to the Add Experiment screen
                        onAddExperiment = {
                            navController.navigate("add_experiment")
                        },

                        // Navigate to the Experiment List screen
                        onViewExperiments = {
                            navController.navigate("experiments")
                        },

                        // Navigate to the Search screen
                        onSearchExperiments = {
                            navController.navigate("search")
                        }
                    )
                }

                // Add Experiment screen
                composable("add_experiment") {
                    AddExperimentScreen(

                        // Add the new experiment to the ViewModel
                        onExperimentAdded = { experiment ->
                            experimentViewModel.addExperiment(experiment)

                            // Return to the previous screen
                            navController.popBackStack()
                        },

                        // Return to the previous screen
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

                // Experiment List screen
                composable("experiments") {

                    ExperimentListScreen(
                        // Display the experiments stored in the ViewModel
                        experiments = experimentViewModel.experiments,

                        // When Edit is clicked, save the selected experiment
                        onEditExperiment = { experiment ->

                            selectedExperiment = experiment

                            // Open the Edit Experiment screen
                            navController.navigate("edit_experiment")
                        },

                        // Delete the selected experiment
                        onDeleteExperiment = { experiment ->
                            experimentViewModel.deleteExperiment(experiment)
                        },

                        // Return to the previous screen
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

                // Edit Experiment screen
                composable("edit_experiment") {

                    // Only show the screen if an experiment was selected
                    selectedExperiment?.let { experiment ->

                        EditExperimentScreen(
                            experiment = experiment,

                            // Save the updated experiment
                            onExperimentUpdated = { updatedExperiment ->

                                experimentViewModel.updateExperiment(
                                    oldExperiment = experiment,
                                    updatedExperiment = updatedExperiment
                                )

                                // Clear the selected experiment
                                selectedExperiment = null

                                // Return to the previous screen
                                navController.popBackStack()
                            },

                            // Go back without saving changes
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }

                // Search Experiment screen
                composable("search") {

                    SearchExperimentScreen(
                        // Use the experiments stored in the ViewModel
                        experiments = experimentViewModel.experiments,

                        // Open the edit screen for the selected experiment
                        onEditExperiment = { experiment ->
                            selectedExperiment = experiment
                            navController.navigate("edit_experiment")
                        },

                        // Delete the selected experiment
                        onDeleteExperiment = { experiment ->
                            experimentViewModel.deleteExperiment(experiment)
                        },

                        // Return to the previous screen
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun HomeScreen(
    onAddExperiment: () -> Unit,
    onViewExperiments: () -> Unit,
    onSearchExperiments: () -> Unit
) {

    // Main layout for the home screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Main application title
        Text(
            text = "Laboratory Management System",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // Short description of the application
        Text(
            text = "Manage your laboratory experiments",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        // Card containing the main menu buttons
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Menu title
                Text(
                    text = "Experiment Management",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                // Button to add a new experiment
                Button(
                    onClick = onAddExperiment,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Add Experiment"
                    )
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                // Button to view all experiments
                OutlinedButton(
                    onClick = onViewExperiments,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "View Experiments"
                    )
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                // Button to search for an experiment
                OutlinedButton(
                    onClick = onSearchExperiments,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Search Experiments"
                    )
                }
            }
        }
    }
}
