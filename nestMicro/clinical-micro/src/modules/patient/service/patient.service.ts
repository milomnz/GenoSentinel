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

    create(patient: Patient): Promise<Patient> {
        return this.patientRepository.save(patient);
    }

    async updateName(id: number, firstName: string): Promise<void> {
        const exists = await this.patientRepository.findOneBy({ id });
        if (!exists) {
            throw new Error('Paciente no encontrado');
        }
        await this.patientRepository.update(Number(id), { firstName }).then(() => { });
    }

    async updateStatus(id: number, status: string): Promise<void> {
        const exists = await this.patientRepository.findOneBy({ id });
        if (!exists) {
            throw new Error('Paciente no encontrado');
        }
        await this.patientRepository.update(Number(id), { status }).then(() => { });
    }

    async delete(id: number): Promise<void> {
        const exists = await this.patientRepository.findOneBy({ id });
        if (!exists) {
            throw new Error('Paciente no encontrado');
        }
        await this.patientRepository.delete(Number(id));
    }
}