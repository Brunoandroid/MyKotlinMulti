package com.example.mykoltinmulti

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class TodoRepositoryTest {

    @Test
    fun add_shouldTrimAndAssignId() {
        val repo = TodoRepository()
        val t1 = repo.add("  First  ")
        val t2 = repo.add("Second")

        assertEquals("First", t1.text)
        assertEquals(1L, t1.id)
        assertEquals(2L, t2.id)
        assertFalse(t1.done)
        assertEquals(listOf(t1, t2), repo.list())
    }

    @Test
    fun add_blankShouldThrow() {
        val repo = TodoRepository()
        assertFailsWith<IllegalArgumentException> { repo.add("   ") }
        assertTrue(repo.list().isEmpty())
    }

    @Test
    fun toggle_shouldFlipDoneFlag() {
        val repo = TodoRepository()
        val t = repo.add("Task")
        val toggled1 = repo.toggle(t.id)
        assertNotNull(toggled1)
        assertTrue(toggled1!!.done)
        val toggled2 = repo.toggle(t.id)
        assertNotNull(toggled2)
        assertFalse(toggled2!!.done)
    }

    @Test
    fun toggle_nonExistingReturnsNull() {
        val repo = TodoRepository()
        assertNull(repo.toggle(999L))
    }

    @Test
    fun remove_shouldDeleteAndReturnTrue() {
        val repo = TodoRepository()
        val t = repo.add("Task")
        assertTrue(repo.remove(t.id))
        assertTrue(repo.list().isEmpty())
    }

    @Test
    fun remove_nonExistingReturnsFalse() {
        val repo = TodoRepository()
        assertFalse(repo.remove(123L))
    }
}