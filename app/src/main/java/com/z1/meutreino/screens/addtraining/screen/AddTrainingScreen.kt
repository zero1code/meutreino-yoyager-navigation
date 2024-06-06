package com.z1.meutreino.screens.addtraining.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.z1.meutreino.ui.components.CustomOutlinedTextInputQuantity
import com.z1.meutreino.ui.components.CustomTopAppBar
import com.z1.meutreino.ui.components.EmptyData
import com.z1.meutreino.ui.theme.MeuTreinoTheme

@Composable
fun AddTrainingScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    val exerciseList = remember {
        mutableStateListOf<Pair<String, Pair<String, String>>>()
    }
    Column(
        modifier = modifier
            .imePadding(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Scaffold(
            modifier = Modifier
                .weight(1f),
            containerColor = MaterialTheme.colorScheme.background,
            topBar = { Header(
                onBackPressed = onBackPressed
            ) }
        ) { padding ->
            ExerciseList(
                modifier = Modifier.padding(padding),
                exerciseList = exerciseList
            )
        }

        FormExercise(
            onAddExerciseClick = { exercise ->
                exerciseList.add(exercise)
            }
        )
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    CustomTopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
                    contentDescription = null
                )
            }
        },
        title = {
            Text(
                text = "Adicionar treino",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    )
}

@Composable
fun ExerciseList(
    modifier: Modifier = Modifier,
    exerciseList: SnapshotStateList<Pair<String, Pair<String, String>>>,
) {
    val listState = rememberLazyListState()

    if (exerciseList.isNotEmpty()) {
        LazyColumn(
            modifier = modifier,
            state = listState
        ) {
            items(exerciseList) { item ->
                ExerciseItem(exercise = item)
            }
        }
    } else {
        EmptyData(
            modifier = Modifier
                .fillMaxSize(),
            message = "Adicione um exercício"
        )
    }
}

@Composable
fun ExerciseItem(
    modifier: Modifier = Modifier,
    exercise: Pair<String, Pair<String, String>>,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        onClick = { /*TODO*/ }
    ) {
        Column(
            modifier = modifier
                .padding(16.dp)
        ) {
            Text(text = exercise.first)

            Row {
                Text(text = "${exercise.second.first}x séries de ")
                Text(text = "${exercise.second.second}x repetições")
            }
        }
    }
}

@Composable
fun FormExercise(
    modifier: Modifier = Modifier,
    onAddExerciseClick: (Pair<String, Pair<String, String>>) -> Unit,
) {
    var exerciseName by remember {
        mutableStateOf("")
    }

    var series by remember {
        mutableStateOf("1")
    }
    var repeticoes by remember {
        mutableStateOf("1")
    }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 30.dp, bottomStart = 0.dp, topEnd = 30.dp, bottomEnd = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        onClick = {  }
    ) {
        Column(
            modifier = modifier
                .padding(bottom = 16.dp)
        ) {
            ExerciseInput(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = "Nome do exercício",
                placeholder = "Digite o nome do exercício",
                value = exerciseName,
                onValueChange = { exerciseName = it },
                isError = false
            )
            Row() {
                CustomOutlinedTextInputQuantity(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    label = "Séries",
                    value = series,
                    onQuantidadeChange = { series = it }
                )
                CustomOutlinedTextInputQuantity(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    label = "Repetições",
                    value = repeticoes,
                    onQuantidadeChange = { repeticoes = it }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.large,
                onClick = {
                    onAddExerciseClick(exerciseName to (series to repeticoes))

                }
            ) {
                Text(
                    text = "Adicionar exercício",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = MaterialTheme.shapes.large,
                onClick = {

                }
            ) {
                Text(
                    text = "Criar novo Treino",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun ExerciseInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    placeholder: String? = null,
    label: String,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        isError = isError,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        colors = OutlinedTextFieldDefaults.colors(),
        label = {
            Text(text = label)
        },
        placeholder = {
            placeholder?.let {
                Text(text = it)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Done,
        ),
        visualTransformation = VisualTransformation.None
    )
}

@Preview(showSystemUi = true)
@Composable
private fun AddTrainingScreenPreview() {
    MeuTreinoTheme {
        AddTrainingScreen(
            onBackPressed = {}
        )
    }
}