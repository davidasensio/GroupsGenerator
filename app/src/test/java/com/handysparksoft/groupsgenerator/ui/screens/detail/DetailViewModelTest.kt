package com.handysparksoft.groupsgenerator.ui.screens.detail

import com.google.common.truth.Truth
import com.handysparksoft.groupsgenerator.model.Participant
import org.junit.Test

class DetailViewModelTest {
    @Test
    fun whenAddParticipant_updateList() {
        val subject = DetailViewModel()
        val expected = Participant("1", "Participant 1")
        subject.addParticipant(expected)
        Truth.assertThat(subject.participants).isEqualTo(listOf(expected))
    }

    @Test
    fun whenRemovingParticipant_updateList() {
        val viewModel = DetailViewModel()
        val participant1 = Participant("1", "Participant 1")
        val participant2 = Participant("2", "Participant 2")
        viewModel.addParticipant(participant1)
        viewModel.addParticipant(participant2)

        viewModel.removeParticipant(participant1)

        Truth.assertThat(viewModel.participants).isEqualTo(listOf(participant2))
    }

    @Test
    fun whenNotEditing_currentEditParticipantIsNull() {
        val subject = DetailViewModel()
        val participant1 = Participant("1", "Participant 1")
        subject.addParticipant(participant1)
        Truth.assertThat(subject.currentEditParticipant).isNull()
    }

    @Test
    fun whenEditing_currentEditParticipantIsCorrect() {
        val subject = DetailViewModel()
        val participant1 = Participant("1", "Participant 1")
        val participant2 = Participant("2", "Participant 2")
        subject.addParticipant(participant1)
        subject.addParticipant(participant2)
        subject.onEditParticipantSelected(participant1)
        Truth.assertThat(subject.currentEditParticipant).isEqualTo(participant1)
    }

    @Test
    fun whenEditDone_currentEditParticipantIsCorrect() {
        val subject = DetailViewModel()
        val participant1 = Participant("1", "Participant 1")
        val participant2 = Participant("2", "Participant 2")
        subject.addParticipant(participant1)
        subject.addParticipant(participant2)
        subject.onEditParticipantSelected(participant1)
        subject.onEditDone()
        Truth.assertThat(subject.currentEditParticipant).isNull()
    }

    @Test
    fun whenEditing_updatesAreShownInParticipantAndList() {
        val subject = DetailViewModel()
        val participant1 = Participant("1", "Participant 1")
        val participant2 = Participant("2", "Participant 2")
        subject.addParticipant(participant1)
        subject.addParticipant(participant2)
        subject.onEditParticipantSelected(participant1)
        val expected = participant1.copy(name = "New name 1")
        subject.onEditParticipantChange(expected)
        Truth.assertThat(subject.participants).isEqualTo(listOf(expected, participant2))
        Truth.assertThat(subject.currentEditParticipant).isEqualTo(expected)
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenEditing_wrongParticipantThrows() {
        val subject = DetailViewModel()
        val participant1 = Participant("1", "Participant 1")
        val participant2 = Participant("2", "Participant 2")
        subject.addParticipant(participant1)
        subject.addParticipant(participant2)
        subject.onEditParticipantSelected(participant1)
        val expected = participant2.copy(name = "New name 1")
        subject.onEditParticipantChange(expected)
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenNotEditing_onParticipantEditChangeThrows() {
        val subject = DetailViewModel()
        val participant1 = Participant("1", "Participant 1")
        subject.onEditParticipantChange(participant1)
    }
}
