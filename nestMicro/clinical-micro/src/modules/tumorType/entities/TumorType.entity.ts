import {Entity, PrimaryGeneratedColumn, Column, OneToMany, ManyToMany, JoinTable, OneToOne} from 'typeorm';
import { ClinicalRecord } from 'src/modules/clinicalRecord/entities/ClinicalRecord.entity';

@Entity('tumortype')
export class TumorType{
    @PrimaryGeneratedColumn()
    id: number;

    @Column({length: 50, unique: true })
    name:string;

    @Column({name: 'systemAffected', length:70 })
    systemAffected: string;
}