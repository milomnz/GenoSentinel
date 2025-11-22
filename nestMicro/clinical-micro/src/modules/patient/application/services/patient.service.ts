import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Patient } from '../../domain/entities/patient.entity';

@Injectable()
export class PatientService {
    constructor(
        @InjectRepository(Patient)
        private patientRepository: Repository<Patient>,
    ) {}

    findAll(): Promise<Patient[]> {
        return this.patientRepository.find();
    }

    create(patient: Patient): Promise<Patient> {
    return this.patientRepository.save(patient);
  }
}