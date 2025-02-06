package de.syntax_institut.feels.Naviagation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import de.syntax_institut.feels.data.MoodEntry
import de.syntax_institut.feels.ui.Models.MoodListViewModel
import de.syntax_institut.feels.ui.Views.CreateNewMoodView
import de.syntax_institut.feels.ui.Views.HomeView
import de.syntax_institut.feels.ui.Views.MoodDetailView
import de.syntax_institut.feels.ui.Views.MoodListView
import de.syntax_institut.feels.ui.Views.SettingsView
import de.syntax_institut.feels.ui.Views.ViewComponents.FullScreenBackground
import de.syntax_institut.feels.ui.theme.FeelsTheme
import kotlinx.serialization.Serializable

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
    viewModel: MoodListViewModel = viewModel(),
    modifier: Modifier = Modifier
) {

    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val isSorted by viewModel.isDarkMode.collectAsState()


    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    FeelsTheme(
        isDarkMode = isDarkMode
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.White, Color(0xFFF6B47C)),
                    )
            )
        ) {

            Scaffold(modifier = Modifier
                .fillMaxSize(),
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = { navController.navigate(CreateNewMoodView) { popUpTo(MoodListView) {
//                    inclusive = true
//                }
//                    launchSingleTop = true
//                    restoreState = false
//                }
//                }) {
//                Icon(Icons.Filled.Add, contentDescription = "Neue Stimmung erstellen")
//            }
//        },
                bottomBar = {
                    NavigationBar(
                        containerColor = Color.Transparent,
                        tonalElevation = 5.dp
                    )
                    {
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
                                    Text(
                                        text = item.label,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold

                                    )

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
                        .background(MaterialTheme.colorScheme.background)
                        .padding(innerPadding)

                ) {
                    composable<HomeView> {
                        HomeView(
                            viewModel = viewModel
                        )
                    }

                    composable<CreateNewMoodView> {
                        CreateNewMoodView(
                            viewModel = viewModel,
                            modifier = Modifier
                        )
                    }

                    composable<SettingsView> {
                        SettingsView(
                            viewModel = viewModel
                        )
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
    Second(MoodListView, "Einträge", Icons.Filled.Menu),
    Third(CreateNewMoodView, "Neuer Eintrag", Icons.Filled.Add),
    Four(SettingsView, "Profil", Icons.Filled.AccountCircle)
}



