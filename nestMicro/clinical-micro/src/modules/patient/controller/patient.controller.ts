/**
 * @author milomnz
 * Endpoint controller for patient entity
 */
import {
  Controller,
  Get,
  Post,
  Delete,
  Body,
  Param,
  ParseIntPipe,
  HttpCode,
  HttpStatus,
  Patch,} 
  

from '@nestjs/common';
import { PatientService } from '../service/patient.service';
import { Patient } from '../entity/patient.entity';
import { ApiOperation, ApiResponse, ApiTags, ApiParam } from '@nestjs/swagger';
import { CreatePatientDto} from '../dto/createPatient.dto'

@ApiTags('patients')
@Controller('patients')
export class PatientController {
  constructor(private readonly patientService: PatientService) {}

  @Get()
  @ApiOperation({ summary: 'Retorna todos los pacientes' })
  @ApiResponse({
    status: 200,
    description: 'Lista de pacientes',
    type: [Patient],
  })
  findAll(): Promise<Patient[]> {
    return this.patientService.findAll();
  }

  @Get(':id')
  @ApiOperation({ summary: 'Retorna un paciente por su id' })
  @ApiParam({ name: 'id', type: 'number', description: 'ID del paciente' })
  @ApiResponse({ status: 200, description: 'El paciente', type: Patient })
  @ApiResponse({ status: 404, description: 'Paciente no encontrado' })
  findById(@Param('id', ParseIntPipe) id: number): Promise<Patient | null> {
    return this.patientService.findById(id);
  }

  @Post()
  @HttpCode(HttpStatus.CREATED)
  @ApiOperation({ summary: 'Crear un nuevo paciente' })
  @ApiResponse({
    status: 201,
    description: 'Paciente creado exitosamente',
    type: Patient,
  })
  @ApiResponse({ status: 400, description: 'Datos inválidos' })
  create(@Body() createPatientDto: CreatePatientDto): Promise<Patient> {
    return this.patientService.create(createPatientDto);
  }

  @Patch(':updatename/:id')
  @ApiOperation({ summary: 'Actualizar el nombre del paciente' })
  @ApiParam({ name: 'id', type: 'number', description: 'ID del paciente' })
  @ApiResponse({ status: 200, description: 'Nombre actualizado exitosamente' })
  @ApiResponse({ status: 404, description: 'Paciente no encontrado' })
  updateName(
    @Param('id', ParseIntPipe) id: number,
    @Body() name : string,
  ): Promise<void> {
    return this.patientService.updateName(id, name);
  }

  @Patch(':updatestatus/:id')
  @ApiOperation({ summary: 'Actualizar el estado del paciente' })
  @ApiParam({ name: 'id', type: 'number', description: 'ID del paciente' })
  @ApiResponse({ status: 200, description: 'Estado actualizado exitosamente' })
  @ApiResponse({ status: 404, description: 'Paciente no encontrado' })
  updateStatus(
    @Param('id', ParseIntPipe) id: number,
    @Body() status: string,
  ): Promise<void> {
    return this.patientService.updateStatus(id, status);
  }

  @Delete(':id')
  @HttpCode(HttpStatus.NO_CONTENT)
  @ApiOperation({ summary: 'Eliminar un paciente' })
  @ApiParam({ name: 'id', type: 'number', description: 'ID del paciente' })
  @ApiResponse({ status: 204, description: 'Paciente eliminado exitosamente' })
  @ApiResponse({ status: 404, description: 'Paciente no encontrado' })
  delete(@Param('id', ParseIntPipe) id: number): Promise<void> {
    return this.patientService.delete(id);
  }
}