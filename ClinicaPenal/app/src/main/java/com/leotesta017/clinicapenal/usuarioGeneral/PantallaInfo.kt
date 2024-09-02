package com.leotesta017.clinicapenal.usuarioGeneral

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.LabelCategoria
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.RoundedButton
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.SearchBar


@Composable
fun PantallaInfo(navController: NavController?) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 140.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                TopBar()
                Spacer(modifier = Modifier.height(16.dp))
                InfoLegalSection()
                Spacer(modifier = Modifier.height(16.dp))
                SearchBar("")
                Spacer(modifier = Modifier.height(16.dp))
                LabelCategoria("Categoria")
                CategoriesSection(navController)
                Spacer(modifier = Modifier.height(16.dp))
                LabelCategoria("Servicios")
                Spacer(modifier = Modifier.height(16.dp))
                ServicesSection(navController)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 95.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RoundedButton(
                icon = Icons.AutoMirrored.Filled.Chat,
                label = "JuriBot",
                onClick = { navController?.navigate("ReviewComentarios") }
            )
            RoundedButton(
                icon = Icons.Default.CalendarToday,
                label = "Solicitud de Cita",
                onClick = { navController?.navigate("crearsolicitud") }
            )
        }

        // Barra de navegación
        BarraNav(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
fun InfoLegalSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Información Legal",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
fun CategoriesSection(navController: NavController?) {
    Column(modifier = Modifier
        .padding(16.dp)
        .padding(top = 0.dp)) {
        CategoryItem("Robo y hurto", "La toma de propiedad ajena sin el consentimiento del propietario, con la intención de no devolverla.", navController)
        Spacer(modifier = Modifier.height(8.dp))
        CategoryItem("Extorsión y amenaza", "Amenazas o coerción para obtener dinero, bienes o servicios de una persona mediante la intimidación.", navController)
        Spacer(modifier = Modifier.height(8.dp))
        CategoryItem("Violencia Doméstica", "Abuso físico, emocional o psicológico dentro del entorno familiar, destinado a controlar o intimidar a un miembro del hogar.", navController)
        Spacer(modifier = Modifier.height(8.dp))
        CategoryItem("Deudas", "Falta de pago de una obligación financiera que puede derivar en acciones legales por parte del acreedor.", navController)
    }
}

@Composable
fun CategoryItem(title: String, description: String, navController: NavController?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
            .clickable {
                navController?.navigate("detalle_info")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = description, fontSize = 14.sp, color = Color.Black)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Flecha para Detalles",
            tint = Color.Blue,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun ServicesSection(navController: NavController?) {
    Column(modifier = Modifier
        .padding(16.dp)
        .padding(top = 0.dp)) {
        ServiceItem("Asesoria Legal", "Consulta profesional donde se " +
                "ofrece orientación y consejo sobre cómo proceder en una situación legal " +
                "específica", navController)
        Spacer(modifier = Modifier.height(8.dp))


        ServiceItem("Representacion Legal", "Actuación en nombre del cliente en" +
                " procesos judiciales o negociaciones, defendiendo sus derechos e " +
                "intereses legales", navController)
        Spacer(modifier = Modifier.height(8.dp))


        ServiceItem("Revision de Documentos Legales", "Análisis detallado de " +
                "contratos, acuerdos y otros documentos legales para garantizar " +
                "su validez", navController)
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun ServiceItem(title: String, description: String, navController: NavController?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
            .clickable {
                navController?.navigate("servicios_info")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = description, fontSize = 14.sp, color = Color.Black)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Flecha para Detalles",
            tint = Color.Blue,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaInfoPreview() {
    PantallaInfo(navController = null)
}
