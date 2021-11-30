package com.handysparksoft.groupsgenerator.ui.screens.detail

import com.google.common.truth.Truth
import com.handysparksoft.groupsgenerator.model.Participant
import org.junit.Test

class DetailViewModelTest {
    @Test
    fun `when add participant should updatelist`() {
        val subject = DetailViewModel()
        val expected = Participant("1", "Participant 1")
        subject.addParticipant(expected)
        Truth.assertThat(subject.participants).isEqualTo(listOf(expected))
    }

    @Test
    fun `when removing participant should updatelist`() {
        val viewModel = DetailViewModel()
        val participant1 = Participant("1", "Participant 1")
        val participant2 = Participant("2", "Participant 2")
        viewModel.addParticipant(participant1)
        viewModel.addParticipant(participant2)

        viewModel.removeParticipant(participant1)

        Truth.assertThat(viewModel.participants).isEqualTo(listOf(participant2))
    }

    @Test
    fun `when not editing currenteditparticipant should be null`() {
        val subject = DetailViewModel()
        val participant1 = Participant("1", "Participant 1")
        subject.addParticipant(participant1)
        Truth.assertThat(subject.currentEditParticipant).isNull()
    }

    @Test
    fun `when editing currenteditparticipant should be correct`() {
        val subject = DetailViewModel()
        val participant1 = Participant("1", "Participant 1")
        val participant2 = Participant("2", "Participant 2")
        subject.addParticipant(participant1)
        subject.addParticipant(participant2)
        subject.onEditParticipantSelected(participant1)
        Truth.assertThat(subject.currentEditParticipant).isEqualTo(participant1)
    }

    @Test
    fun `when edit done currenteditparticipant should be correct`() {
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
    fun `when editing updates are shown in participant and list`() {
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
    fun `when editing wrong participant should throw exception`() {
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
    fun `when not editing on participant then edit change should throw exception`() {
        val subject = DetailViewModel()
        val participant1 = Participant("1", "Participant 1")
        subject.onEditParticipantChange(participant1)
    }
}
