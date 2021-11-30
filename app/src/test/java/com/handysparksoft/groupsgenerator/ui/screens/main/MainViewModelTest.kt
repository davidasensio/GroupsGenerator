package com.handysparksoft.groupsgenerator.ui.screens.main

import android.content.Context
import com.google.common.truth.Truth
import com.handysparksoft.groupsgenerator.data.Prefs
import com.handysparksoft.groupsgenerator.model.AList
import com.handysparksoft.groupsgenerator.model.Participant
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @Mock
    lateinit var mockContext: Context

    @Mock
    lateinit var mockPrefs: Prefs

    private val subject
        get() = MainViewModel(mockPrefs)

    private val listOne
        get() = AList(
            id = "1",
            name = "List one",
            description = "Description List one"
        )
    private val participantOne
        get() = Participant(id = "1", name = "Participant 1")

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test fun `sample test`() {
    }

    @Ignore
    fun `when retrieve initial lists should be empty`() {
        val initialLists = subject.aLists
        Truth.assertThat(initialLists.size).isEqualTo(0)
    }

    // TODO: Complete all the case once the Prefs are mocked correctly
}
