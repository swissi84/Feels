package de.syntax_institut.feels

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import de.syntax_institut.feels.data.MoodEntry
import de.syntax_institut.feels.ui.Views.CreateNewMoodView
import de.syntax_institut.feels.ui.Views.HomeView
import de.syntax_institut.feels.ui.Views.MoodDetailView
import de.syntax_institut.feels.ui.Views.MoodListView
import de.syntax_institut.feels.ui.Views.SettingsView
import de.syntax_institut.feels.ui.theme.FeelsTheme
import kotlinx.serialization.Serializable

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "sorted")

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FeelsTheme {
                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                Scaffold(modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { navController.navigate(CreateNewMoodView) { popUpTo(MoodListView) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                                restoreState = false
                            }
                        }) {
                            Icon(Icons.Filled.Add, contentDescription = "Neue Stimmung erstellen")
                        }
                    },
                    bottomBar = {
                        NavigationBar {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination

                            NavItem.entries.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = currentBackStackEntry?.destination?.hasRoute(item.route::class)
                                        ?: false,
                                    onClick = {
                                        navController.navigate(item.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = item.icon,
                                            contentDescription = item.label
                                        )
                                    },
                                    label = {
                                        Text(item.label)
                                    },
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = HomeView,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        composable<HomeView> {
                            HomeView(
                                viewModel = viewModel()
                            )
                        }

                        composable<CreateNewMoodView> {
                           CreateNewMoodView(
                               viewModel = viewModel(),
                               modifier = Modifier
                           )
                        }

                        composable<SettingsView> {
                           SettingsView()
                        }

                        composable<MoodListView> {
                            MoodListView(
                                onNavigateToMoodDetailView = { moodEntry: MoodEntry ->
                                    navController.navigate(
                                        MoodDetailRoute(
                                            name = moodEntry.name,
                                            mood = moodEntry.mood,
                                            moodFactor = moodEntry.moodFactor,
                                            timestamp = moodEntry.timestamp,
                                            moodText = moodEntry.moodText,
                                            moodWeather = moodEntry.moodWeather,
                                            )
                                    )
                                }
                            )
                        }

                        composable<MoodDetailRoute> {
                            val moodDetailRoute = it.toRoute<MoodDetailRoute>()
//                            Log.d("MoodDetailRoute", MoodDetailRoute.toString())

                            MoodDetailView(
                                moodEntry = MoodEntry(
                                    name = moodDetailRoute.name,
                                    mood = moodDetailRoute.mood,
                                    moodFactor = moodDetailRoute.moodFactor,
                                    timestamp = moodDetailRoute.timestamp,
                                    moodText = moodDetailRoute.moodText,
                                    moodWeather = moodDetailRoute.moodWeather
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}


@Serializable
object HomeView

@Serializable
object MoodListView

@Serializable
object CreateNewMoodView

@Serializable
object SettingsView

@Serializable
data class MoodDetailRoute(
    val name: String,
    val mood: Double,
    val moodFactor: String,
    val moodWeather: String,
    val moodText: String,
    val timestamp: Long,
)

enum class NavItem(
    val route: Any,
    val label: String,
    val icon: ImageVector,
) {
    First(HomeView, "Home", Icons.Filled.Home),
    Second(MoodListView, "Moods", Icons.Filled.Menu),
    Third(CreateNewMoodView, "NewMood", Icons.Filled.Add),
    Four(SettingsView, "Settings", Icons.Filled.Settings)

}

