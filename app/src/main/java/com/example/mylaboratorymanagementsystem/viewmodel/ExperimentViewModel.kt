package com.example.mylaboratorymanagementsystem.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.mylaboratorymanagementsystem.model.Experiment
import java.util.UUID

class ExperimentViewModel : ViewModel() {

    private val _experiments = mutableStateListOf<Experiment>()

    val experiments: List<Experiment>
        get() = _experiments

    fun addExperiment(experiment: Experiment) {

        val experimentWithId = Experiment(
            id = UUID.randomUUID().toString(),
            name = experiment.name,
            description = experiment.description,
            date = experiment.date,
            temperature = experiment.temperature,
            pH = experiment.pH,
            treatmentTime = experiment.treatmentTime,
            results = experiment.results
        )

        _experiments.add(experimentWithId)
    }

    fun updateExperiment(
        oldExperiment: Experiment,
        updatedExperiment: Experiment
    ) {

        val index = _experiments.indexOfFirst {
            it.id == oldExperiment.id
        }

        if (index != -1) {
            _experiments[index] = updatedExperiment
        }
    }

    fun deleteExperiment(experiment: Experiment) {

        _experiments.removeAll {
            it.id == experiment.id
        }
    }
}
