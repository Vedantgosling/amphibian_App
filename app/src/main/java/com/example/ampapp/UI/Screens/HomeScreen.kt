package com.example.ampapp.UI.Screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ampapp.Model.Amp
import com.example.ampapp.R
import com.example.ampapp.UI.theme.Purple40


@Composable
fun HomeScreen(
ampUiState: AmpUiState,
modifier: Modifier,
contentPadding: PaddingValues = PaddingValues(0.dp),

) {
    when(ampUiState){
        is AmpUiState.Loading -> LoadingScreen(modifier.size(200.dp))
        is AmpUiState.Success -> AmphibiansListScreen(
            amps = ampUiState.amps,
            modifier = modifier
                .padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    top = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium)
                ),
            contentPadding = contentPadding)
        else -> ErrorScreen(modifier)
    }
    
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = "Loading"
    )
}

@Composable
fun ErrorScreen( modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_broken_image),
            contentDescription = "Error"
        )
        Text(text = "Loading Failed", modifier = Modifier.padding(16.dp))


    }
}

@Composable
fun AmpCard(
    amp: Amp,
    modifier: Modifier = Modifier
    ){
    var expanded by remember { mutableStateOf(false) }
    Card(modifier = modifier,
        shape = RoundedCornerShape(8.dp))
    {

        Column {
            Text(
                text = stringResource(R.string.amphibian_title, amp.name, amp.type),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(amp.imgSrc)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img)
            )
            SupItemButton(expanded = expanded, onClick = { expanded = !expanded } )

            if(expanded) {
                SupeHobby(
                    amp.description,
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(R.dimen.Padding_Small),
                            top = dimensionResource(R.dimen.Padding_Small),
                            end = dimensionResource(R.dimen.Padding_Medium),
                            bottom = dimensionResource(R.dimen.Padding_Medium)
                        )
                        .animateContentSize(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioNoBouncy,
                                stiffness = Spring.StiffnessMedium
                            )
                        )
                )
            }
        }

    }

}

@Composable
private fun AmphibiansListScreen(
    amps: List<Amp>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(
            items = amps,
            key = { amp ->  amp.name }
        ) { amp ->
            AmpCard(amp = amp, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun SupItemButton(
    expanded:Boolean,
    onClick: ()-> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = colorResource(id = R.color.teal_200),
            contentColor = Color.Magenta
        ), modifier = Modifier
            .size(40.dp)
            .padding(6.dp)
            .offset(x = 165.dp)) {
        Icon(imageVector = if(expanded)
            Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = "Description"
        )

    }
}

@Composable
fun SupeHobby(
    ampd: String,
    modifier: Modifier = Modifier
){
    val gradientColors = listOf(Cyan, Blue, Purple40)

    Column(
        modifier = modifier
    ) {
        Text(
            text = "about",
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = ampd, fontSize = 18.sp,style = TextStyle(
                brush = Brush.linearGradient(
                    colors = gradientColors
                )
            )


        )
    }
}
