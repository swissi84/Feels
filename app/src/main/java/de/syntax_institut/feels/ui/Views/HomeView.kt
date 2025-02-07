package de.syntax_institut.feels.ui.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.feels.R
import de.syntax_institut.feels.data.MoodEntry
import de.syntax_institut.feels.ui.Models.MoodListViewModel
import de.syntax_institut.feels.ui.Views.ViewComponents.FullScreenBackground
import de.syntax_institut.feels.ui.Views.ViewComponents.MoodChart
import de.syntax_institut.feels.ui.Views.ViewComponents.MoodIndicator
import de.syntax_institut.feels.ui.Views.ViewComponents.RotatingCircle
import de.syntax_institut.feels.ui.theme.FeelsTheme
import de.syntax_institut.feels.ui.theme.lemonYellow
import de.syntax_institut.feels.ui.theme.skyBlue
import java.time.LocalDate


@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    viewModel: MoodListViewModel,
    ) {

    val isDarkMode by viewModel.isDarkMode.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
//            .background(
//                brush = Brush.linearGradient(
//                    colors = listOf(Color.White, Color(0xFFFFE4C7)),
//                    start = Offset(0.0f, 0.0f),
//                    end = Offset(0.0f, 0.0f)
//                )
//            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Image(
                painter = painterResource(id = R.drawable.feelstitel),
                contentDescription = "Header Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier

                    .scale(1f)
                    .size(420.dp, 200.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Stimmungs Tendenz:", fontSize = 30.sp, modifier = Modifier.padding(8.dp))

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .graphicsLayer(
                        shadowElevation = 15f,
                        shape = CircleShape,
                        clip = true
                    )
                    .background(Color.White)
            ) {
                MoodIndicator(viewModel = viewModel)


            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Stimmungen der letzten 7 Tage:",
                fontSize = 30.sp,
                modifier = Modifier.padding(8.dp)
            )

            MoodChart(viewModel = viewModel)
        }
    }
}







