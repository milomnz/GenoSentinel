/**
 * @author milomnz
 * Endpoint controller for patient entity
 */
import {
  Controller,
  Get,
  Post,
  Put,
  Delete,
  Body,
  Param,
  ParseIntPipe,
  HttpCode,
  HttpStatus,
  Patch,
} from '@nestjs/common';
import { PatientService } from '../service/patient.service';
import { Patient } from '../entity/patient.entity';
import { CreatePatientDto } from '../dto/create-patient.dto';
import { UpdatePatientNameDto } from '../dto/update-patient-name.dto';
import { UpdatePatientStatusDto } from '../dto/update-patient-status.dto';
import { ApiOperation, ApiResponse, ApiTags, ApiParam } from '@nestjs/swagger';
import { CreatePatientDto } from '../dto/createPatient.dto';
import { UpdatePatientNameDto } from '../dto/updatePatientName.dto';
import { UpdatePatientStatusDto } from '../dto/updatePatientStatus.dto';


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

  @Patch('updatename/:id')
  @ApiOperation({ summary: 'Actualizar el nombre del paciente' })
  @ApiResponse({ status: 200, description: 'Nombre actualizado exitosamente', type : Patient})
  @ApiResponse({ status: 404, description: 'Paciente no encontrado' })
  updateName(
    @Param('id', ParseIntPipe) id: number,
<<<<<<< Updated upstream
    @Body() updateNameDto: UpdatePatientNameDto,
  ): Promise<void> {
    return this.patientService.updateName(id, updateNameDto.name);
=======
    @Body() UpdatePatientNameDto: UpdatePatientNameDto,
  ): Promise<void> {
    return this.patientService.updateName(id , UpdatePatientNameDto);
>>>>>>> Stashed changes
  }

  @Patch('updatestatus/:id')
  @ApiOperation({ summary: 'Actualizar el estado del paciente' })
  @ApiResponse({ status: 404, description: 'Paciente no encontrado' })
  updateStatus(
    @Param('id', ParseIntPipe) id: number,
<<<<<<< Updated upstream
    @Body() updateStatusDto: UpdatePatientStatusDto,
  ): Promise<void> {
    return this.patientService.updateStatus(id, updateStatusDto.status);
=======
    @Body() UpdatePatientStatusDto: UpdatePatientStatusDto,
  ): Promise<void> {
    return this.patientService.updateStatus(id ,UpdatePatientStatusDto);
>>>>>>> Stashed changes
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