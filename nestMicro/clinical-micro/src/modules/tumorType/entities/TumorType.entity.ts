import {Entity, PrimaryGeneratedColumn, Column, OneToMany, ManyToMany, JoinTable} from 'typeorm';
import { ClinicalRecord } from 'src/modules/clinicalRecord/entities/ClinicalRecord.entity';

@Entity('tumor_type')
export class TumorType{
    @PrimaryGeneratedColumn('increment')
    id: number;

    @Column({length: 50, unique: true })
    name:string;

    @Column({name: 'systemAffected', length:70 })
    systemAffected: string;
}