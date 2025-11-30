import { Entity, PrimaryGeneratedColumn, Column, OneToMany } from 'typeorm';
import { ClinicalRecord } from '../../clinicalRecord/entities/ClinicalRecord.entity';

@Entity()
export class Patient {
    @PrimaryGeneratedColumn('increment', { type: 'bigint' })
    id?: number;

    @Column({ length: 100, nullable: false })
    firstName: string;

    @Column({ length: 100, nullable: false })
    lastName: string;

    @Column({ type: 'date', nullable: false })
    birthDate: Date;

    @Column({ length: 20, nullable: true })
    gender: string;

    @Column({ length: 20, nullable: true })
    status: string;

    //@OneToMany(() => ClinicalRecord, clinicalRecord => clinicalRecord.patient)
    clinicalRecords?: ClinicalRecord[];
}