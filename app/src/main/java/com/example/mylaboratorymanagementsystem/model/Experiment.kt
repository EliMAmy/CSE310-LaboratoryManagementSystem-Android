package com.example.mylaboratorymanagementsystem.model

class Experiment (
    val id: String = "",
    val name: String,
    val description: String,
    val date: String,
    val temperature: Double,
    val pH: Double,
    val treatmentTime: Double,
    val results: List<String>
)

