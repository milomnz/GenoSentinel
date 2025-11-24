import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { ClinicalRecord } from '../entities/ClinicalRecord.entity';
import { CreateClinicalRecordDto } from '../dto/create-clinicalRecord';
import { Patient } from '../../patient/entity/patient.entity';
import { TumorType } from 'src/modules/tumorType/entities/TumorType.entity';
import { UpdateClinicalRecordDto } from '../dto/update-clinicalRecord';

@Injectable()
export class ClinicalRecordService {
    constructor(
        @InjectRepository(ClinicalRecord)
        private clinicalRecordRepository: Repository<ClinicalRecord>,
        @InjectRepository(Patient)
        private patientRepository: Repository<Patient>,
    ) { }

    async findAll(): Promise<ClinicalRecord[]> {
        return this.clinicalRecordRepository.find({
            relations: ['idPatient', 'tumorType']
        });
    }

    async findById(id: number): Promise<ClinicalRecord> {
        const record = await this.clinicalRecordRepository.findOne({
            where: { id },
            relations: ['idPatient', 'tumorType']
        });
        if (!record) {
            throw new NotFoundException('Clinical Record with ID ${id} not found');
        }
        return record;
    }


    async create(dto: CreateClinicalRecordDto): Promise<ClinicalRecord> {
        const tumorTypeEntities = dto.idTumorTypes.map(id => ({ id: Number(id) } as TumorType));
        const newRecord = this.clinicalRecordRepository.create({
            idPatient: { id: Number(dto.idPatient) } as Patient,
            tumorTypes: tumorTypeEntities,
            diagnosticDate: dto.diagnosticDate,
            stage: dto.stage,
            treatmentProtocol: dto.treatmentProtocol,
        });
        return this.clinicalRecordRepository.save(newRecord);
    }

    async updatePartial(id: number, updateDto: UpdateClinicalRecordDto): Promise<ClinicalRecord> {
        const record = await this.clinicalRecordRepository.findOneBy({ id });
        if (!record) {
            throw new NotFoundException('Clinical Record with ID ${id} not found');
        }

        const updateFields: any = {};

        if (updateDto.idPatient) {
            updateFields.idPatient = { id: Number(updateDto.idPatient) };
        }

        if (updateDto.idTumorTypes && Array.isArray(updateDto.idTumorTypes)) {
            updateFields.tumorTypes = updateDto.idTumorTypes.map(id => ({ id: Number(id) } as TumorType));
        }

        Object.assign(updateFields, updateDto);

        await this.clinicalRecordRepository.update(id, updateFields);
        return this.findById(id);

    }


    async delete(id: number): Promise<void> {
        const result = await this.clinicalRecordRepository.delete(id);
        if (result.affected === 0) {
            throw new NotFoundException(`Clinical Record with ID ${id} not found`);
        }
    }



}