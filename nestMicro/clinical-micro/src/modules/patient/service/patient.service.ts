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
@Injectable()
export class PatientService {
    constructor(
        @InjectRepository(Patient)
        private patientRepository: Repository<Patient>,
    ) { }

    findAll(): Promise<Patient[]> {
        return this.patientRepository.find();
    }
    findById(id: number): Promise<Patient | null> {
        return this.patientRepository.findOneBy({ id });
    }

    create(patient: Patient): Promise<Patient> {
        return this.patientRepository.save(patient);
    }

    async updateName(id: number, name: string): Promise<void> {
        const exists = await this.patientRepository.findOneBy({ id });
        if (!exists) {
            throw new Error('Paciente no encontrado');
        }
        await this.patientRepository.update(Number(id), {  }).then(() => { });
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