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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color




class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val experimentViewModel: ExperimentViewModel = viewModel()
            var selectedExperiment by remember {
                mutableStateOf<Experiment?>(null)
            }
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") {
                    HomeScreen(
                        onAddExperiment = {
                            navController.navigate("add_experiment")
                        },
                        onViewExperiments = {
                            navController.navigate("experiments")
                        } ,
                        onSearchExperiments = {
                            navController.navigate("search")
                        }
                    )
                }

                composable("add_experiment") {
                    AddExperimentScreen(
                        onExperimentAdded = { experiment ->
                            experimentViewModel.addExperiment(experiment)
                            navController.popBackStack()
                        },
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }
                composable("experiments") {

                    ExperimentListScreen(
                        experiments = experimentViewModel.experiments,
                        onEditExperiment = { experiment ->

                            selectedExperiment = experiment
                            navController.navigate("edit_experiment")
                        },

                        onDeleteExperiment = { experiment ->
                            experimentViewModel.deleteExperiment(experiment)
                        },
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }
                composable("edit_experiment") {

                    selectedExperiment?.let { experiment ->

                        EditExperimentScreen(
                            experiment = experiment,
                            onExperimentUpdated = { updatedExperiment ->

                                experimentViewModel.updateExperiment(
                                    oldExperiment = experiment,
                                    updatedExperiment = updatedExperiment
                                )
                                selectedExperiment = null
                                navController.popBackStack()
                            },
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
                composable("search") {
                    SearchExperimentScreen(
                        experiments = experimentViewModel.experiments,

                        onEditExperiment = { experiment ->
                            selectedExperiment = experiment
                            navController.navigate("edit_experiment")
                        },

                        onDeleteExperiment = { experiment ->
                            experimentViewModel.deleteExperiment(experiment)
                        },
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Text(
            text = "Laboratory Management System",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )


        Text(
            text = "Manage your laboratory experiments",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        // Main menu card
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

                Text(
                    text = "Experiment Management",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                // Add Experiment
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

                // View Experiments
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

                // Search Experiments
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


