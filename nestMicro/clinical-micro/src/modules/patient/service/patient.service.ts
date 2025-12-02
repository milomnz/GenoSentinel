/**
 * 
 * @author milomnz
 * Logica de negocio para la entidad patient
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


    /** Retorna toda la lista de pacientes
     * 
     * Promise representa una lista de objetos Patient y que es una 
     * funcion asincrona
     * 
     *  */    
    findAll(): Promise<Patient[]> {
        return this.patientRepository.find();
    }


    /** Retorna un paciente por su id
     * 
     * @param id
     * @return Promise<Patient>
    */
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
    
    create(patient: CreatePatientDto): Promise<Patient> {
        return this.patientRepository.save(patient);
    }

    /** Actualiza el nombre de un paciente
     * 
     * @param DTO de actualizar nombre de paciente
     * @return Promise<void>
     * */

    async updateName(id: number ,dto : UpdatePatientNameDto): Promise<void> {
        const exists = await this.patientRepository.findOneBy({ id });
        if (!exists) {
            throw new NotFoundException(`Patient with ID ${id} not found`);
        }
<<<<<<< Updated upstream
        await this.patientRepository.update(Number(id), { firstName }).then(() => { });
=======
        await this.patientRepository.update(id, { firstName : dto.firstName }).then(() => { });
>>>>>>> Stashed changes
    }

    /** Actualiza el estado de un paciente
     * 
     * @param DTO de actualizar estado de paciente
     * @return Promise<void>
     * */

    async updateStatus(id : number, dto : UpdatePatientStatusDto): Promise<void> {
        const exists = await this.patientRepository.findOneBy({ id });
        if (!exists) {
            throw new NotFoundException(`Patient with ID ${id} not found`);
        }
        await this.patientRepository.update(Number(id), { status }).then(() => { });
    }

    async delete(id: number): Promise<void> {
        const exists = await this.patientRepository.findOneBy({ id });
        if (!exists) {
            throw new NotFoundException(`Patient with ID ${id} not found`);
        }
        await this.patientRepository.delete(id);
    }
}