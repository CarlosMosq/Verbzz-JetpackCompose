package com.company.verbzz_app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.company.verbzz_app.R
import com.company.verbzz_app.ui.theme.WindowMeasurement
import com.company.verbzz_app.utils.Constants.listOfLanguages

@Composable
fun DrawerContent(measurement: WindowMeasurement, onClick: (String) -> Unit) {
    val context = LocalContext.current
    Surface(modifier = Modifier.wrapContentSize()) {
        Column(
            modifier = Modifier.wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = context.getString(R.string.chooseLanguage),
                modifier = Modifier.padding(top = 15.dp),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onSecondary
            )
            LazyRow(
                modifier = Modifier
                    .height((measurement.biggest / 4).dp)
                    .wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                items(listOfLanguages) { language ->
                    Image(
                        painter = when(language) {
                            context.getString(R.string.english) ->
                                painterResource(id = R.drawable.english_language)
                            context.getString(R.string.french) ->
                                painterResource(id = R.drawable.french_language)
                            else -> painterResource(id = R.drawable.non_chosen_language)
                        },
                        contentDescription = stringResource(id = R.string.menuDrawer),
                        modifier = Modifier
                            .height((measurement.biggest / 8).dp)
                            .padding(start = 10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                onClick.invoke(language)
                            }
                    )
                }
            }
        }


    }

}