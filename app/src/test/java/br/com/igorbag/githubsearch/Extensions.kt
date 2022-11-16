package br.com.igorbag.githubsearch

import io.mockk.MockKAnnotations

fun Any.initMockkAnnotations() {
    MockKAnnotations.init(this, relaxUnitFun = true)
}