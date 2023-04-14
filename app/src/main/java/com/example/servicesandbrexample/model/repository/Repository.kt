package com.example.servicesandbrexample.model.repository

import com.example.servicesandbrexample.model.entities.Description

interface Repository {
    fun getTranslationResponse(): ArrayList<Description>
}