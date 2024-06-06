package com.z1.meutreino.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.RemoveCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.z1.meutreino.ui.theme.MeuTreinoTheme

@Composable
fun CustomOutlinedTextInputQuantity(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onQuantidadeChange: (String) -> Unit,
) {
    var botaoPlusPressionado by remember { mutableStateOf(false) }
    var quantidade = value.toInt()


    Box(
        modifier = modifier
            .wrapContentWidth(),
        contentAlignment = Alignment.Center
    ) {
        OutlinedTextField(
            modifier = Modifier,
            visualTransformation = VisualTransformation.None,
            value = " ",
            onValueChange = {},
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            colors = OutlinedTextFieldDefaults.colors(),
            readOnly = true,
            label = {
                Text(text = label)
            },
        )

            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(
                        top = 6.dp,
                        start = 8.dp
                    ),
                onClick = {
                    botaoPlusPressionado = false
                    if (quantidade != 1) quantidade--
                    onQuantidadeChange(quantidade.toString())
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.RemoveCircleOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 6.dp),
                text = quantidade.toString(),
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.1.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(
                        top = 6.dp,
                        end = 8.dp
                    ),
                onClick = {
                    botaoPlusPressionado = true
                    quantidade++
                    onQuantidadeChange(quantidade.toString())
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.AddCircleOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
    }
}

@Preview
@Composable
fun CustomAdicionarQuantidadePreview() {
    MeuTreinoTheme {
        CustomOutlinedTextInputQuantity(
            label = "Séries",
            onQuantidadeChange = {},
            value = "0"
        )
    }
}