/**
 * 
 * @author milomnz
 * Businnes logic for patient entity
 * 
 */
import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Patient } from '../entity/patient.entity';
import { NotFoundException } from '@nestjs/common';
import { CreatePatientDto } from '../dto/createPatient.dto';
import { UpdatePatientNameDto } from '../dto/updatePatientName.dto';
import { UpdatePatientStatusDto } from '../dto/updatePatientStatus.dto';
@Injectable()
export class PatientService {
    constructor(
        @InjectRepository(Patient)
        private patientRepository: Repository<Patient>,
    ) { }

    findAll(): Promise<Patient[]> {
        return this.patientRepository.find();
    }
    async findById(id: number): Promise<Patient> {
    const patient = await this.patientRepository.findOneBy({ id });

    if (!patient) {
      throw new NotFoundException(`Patient with ID ${id} not found`);
        }
    return patient;

    }

    /** Crea un nuevo paciente
     * 
     * @param DTO de crear paciente
     * @return Promise<Patient>
     */
    
    create(dto: CreatePatientDto): Promise<Patient> {
        const newPatient = this.patientRepository.create(dto);
        return this.patientRepository.save(newPatient);
    }

    /** Actualiza el nombre de un paciente
     * 
     * @param DTO de actualizar nombre de paciente
     * @return Promise<void>
     * */

    async updateName(id: number, dto: UpdatePatientNameDto): Promise<void> {
        const exists = await this.patientRepository.findOneBy({ id });
        if (!exists) {
            throw new Error('Paciente no encontrado');
        }
        await this.patientRepository.update(id, { firstName : dto.firstName });
    }

    async updateStatus(id: number, dto: UpdatePatientStatusDto): Promise<void> {
        const exists = await this.patientRepository.findOneBy({ id });
        if (!exists) {
            throw new Error('Paciente no encontrado');
        }
        await this.patientRepository.update(id, { status : dto.status });
    }

    async delete(id: number): Promise<void> {
        const exists = await this.patientRepository.findOneBy({ id });
        if (!exists) {
            throw new Error('Paciente no encontrado');
        }
        await this.patientRepository.delete(id);
    }
}