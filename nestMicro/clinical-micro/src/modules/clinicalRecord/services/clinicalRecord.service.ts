import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { ClinicalRecord } from '../entities/ClinicalRecord.entity';
import { CreateClinicalRecordDto } from '../dto/createclinicalRecord.dto';
import { UpdateClinicalRecordDto } from '../dto/updateClinicalRecord.dto';
import { Patient } from '../../patient/entity/patient.entity';
import { TumorType } from 'src/modules/tumorType/entities/TumorType.entity';
/**
 * @author mendez
 * Endpoint controller for clinicalRecord entity
 */
@Injectable()
export class ClinicalRecordService {
    constructor(
        @InjectRepository(ClinicalRecord)
        private clinicalRecordRepository: Repository<ClinicalRecord>,
        @InjectRepository(Patient)
        private patientRepository: Repository<Patient>,
        @InjectRepository(TumorType)
        private tumorTypeRepository: Repository<TumorType>,
    ) { }

    /** Retorna toda la lista de registros clinicos
     *  
     * Promise representa una lista de objetos ClinicalRecord y que es una
     * funcion asincrona
     * 
     * */


    async findAll(): Promise<ClinicalRecord[]> {
        return this.clinicalRecordRepository.find({
            // Nota: usamos el nombre de la propiedad en la entidad ("patient", "tumorType")
            relations: ['patient', 'tumorType'] 
        });
    }

    /** Retorna un registro clinico por su id
     * @param id
     * @return Promise<ClinicalRecord>
     * */
    
    async findById(id: number): Promise<ClinicalRecord> {
        const record = await this.clinicalRecordRepository.findOne({
            where: { id },
            relations: ['patient', 'tumorType']
        });
        if (!record) {
            throw new NotFoundException(`Clinical Record with ID ${id} not found`);
        }
        return record;
    }

    /** Crea un nuevo registro clinico
     * @param DTO de crear registro clinico
     * @return Promise<ClinicalRecord>
     * */


    async create(dto: CreateClinicalRecordDto): Promise<ClinicalRecord> {
        // 1. Buscamos el paciente
        const patient = await this.patientRepository.findOneBy({ id: dto.idPatient });
        if (!patient) {
            throw new NotFoundException(`Patient with ID ${dto.idPatient} not found`);
        }
        const tumorType = await this.tumorTypeRepository.findOneBy({ id: dto.idTumorType });
        if (!tumorType) {
            throw new NotFoundException(`Tumor Type with ID ${dto.idTumorType} not found`);
        }
        const newRecord = this.clinicalRecordRepository.create({
            patient: patient,
            tumorType : tumorType ,
            diagnosticDate: dto.diagnosticDate,
            stage: dto.stage,
            treatmentProtocol: dto.treatmentProtocol
        });
        return this.clinicalRecordRepository.save(newRecord);
    }

    /**
     * 
     * @param updateDto con el id, stage, y treatment protocol.
     * @returns el resultado de la actualizacion. 
     */



    async updatePartial(id: number, updateDto: UpdateClinicalRecordDto): Promise<void> {
    const exists = await this.clinicalRecordRepository.findOneBy({ id });
    if (!exists) {
      throw new NotFoundException(`Clinical Record with ID ${id} not found`);
    }
    const { stage, treatmentProtocol } = updateDto;
    await this.clinicalRecordRepository.update(id, { stage, treatmentProtocol });
  }

  /**
   * 
   * @param id 
   * @returns resultado de la operacion
   * 
   */

    async delete(id: number): Promise<void> {
        const result = await this.clinicalRecordRepository.delete(id);
        if (result.affected === 0) {
            throw new NotFoundException(`Clinical Record with ID ${id} not found`);
        }
    }
}