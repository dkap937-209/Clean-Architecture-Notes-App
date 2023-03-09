package com.dk.cleanarchitecturenotesapp.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dk.cleanarchitecturenotesapp.feature_note.presentation.add_edit_note.components.AddEditNoteScreen
import com.dk.cleanarchitecturenotesapp.feature_note.presentation.notes.NotesScreen
import com.dk.cleanarchitecturenotesapp.feature_note.presentation.util.Screen
import com.dk.cleanarchitecturenotesapp.ui.theme.CleanArchitectureNotesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanArchitectureNotesAppTheme {
                 Surface(
                    color = MaterialTheme.colors.background
                ){
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesScreen.route
                    ){
                        composable(route = Screen.NotesScreen.route){
                            NotesScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditNotesScreen.route +
                                    "?noteId={noteId}&noteColour={noteColour}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteId"
                                ){
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "noteColour"
                                ){
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ){
                            val colour = it.arguments?.getInt("noteColour")?:-1
                            AddEditNoteScreen(
                                navController = navController,
                                noteColor = colour
                            )
                        }
                    }
                }
            }
        }
    }
}