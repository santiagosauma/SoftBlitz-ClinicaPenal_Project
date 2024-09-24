package com.leotesta017.clinicapenal.view.usuarioEstudiante


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.leotesta017.clinicapenal.model.CasosRepresentacion
import com.leotesta017.clinicapenal.model.SolicitudAdmin
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.CasoRepresentacionItem
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.EstudiantesBarraNav
import com.leotesta017.clinicapenal.view.theme.ClinicaPenalTheme
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SolicitudAdminItem
import com.leotesta017.clinicapenal.view.templatesPantallas.GenerarSolicitudPantallaTemplatenavController


@Composable
fun GenerarSolicitudEstudiante(
    navController: NavController?
) {
    var solicitudes by remember {
         mutableStateOf(
             listOf(
                SolicitudAdmin(
                    id = "ID123",
                    titulo = "Asalto",
                    nombreUsuario = "Fernando Balleza",
                    fechaRealizada = "19/07/2024",
                    estado = "Finalizado",
                    estadoColor = Color.Green
                ),
             // Otras solicitudes...
            )
         )
    }

    var casosRepresentacion by remember {
        mutableStateOf(
            listOf(
                CasosRepresentacion(
                    id = "ID123",
                    tipo = "Asalto",
                    usuarioAsignado = "Fernando Balleza",
                    fechaRealizada = "19/07/2024",
                    estado = "Finalizado",
                    estadoColor = Color.Green
                ),
                // Otros casos...
            )
        )
    }

    GenerarSolicitudPantallaTemplatenavController(
        navController = navController,
        titulo1 = "Citas",
        items1 = solicitudes,
        itemComposable1 = { solicitud ->
            SolicitudAdminItem(
                solicitud = solicitud as SolicitudAdmin,
                navController = navController,
                onDelete = { id ->
                    // Lógica de eliminación
                    solicitudes = solicitudes.filterNot {it.id == id}
                },
                route = "detallecasoestudiante"
            )
        },
        titulo2 = "Casos Representación",
        items2 = casosRepresentacion,
        itemComposable2 = { caso ->
            CasoRepresentacionItem(
                casosRepresentacion = caso as CasosRepresentacion,
                navController = navController,
                onDelete = { id ->
                    // Lógica de eliminación
                    casosRepresentacion = casosRepresentacion.filterNot { it.id == id }
                },
                route = "detallecasoestudiante"
            )
        },
        barraNavComposable = {
            Box(modifier = Modifier.fillMaxSize())
            {
                EstudiantesBarraNav(
                    navController = navController,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                )
            }

        }
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewGenerarSolicitudEstudiante() {
    ClinicaPenalTheme {
        GenerarSolicitudEstudiante(navController = rememberNavController())
    }
}
