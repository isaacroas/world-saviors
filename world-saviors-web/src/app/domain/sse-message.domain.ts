import { Deed } from './deed.domain';

export class SseMessage {

  influencerFollowers: number;
  ufoSightings: number;
  leaderSupporters: number;
  deeds: Array<Deed>;

  constructor(influencerFollowers: number, ufoSightings: number, leaderSupporters: number, deeds: Array<Deed>) {
    this.influencerFollowers = influencerFollowers;
    this.ufoSightings = ufoSightings;
    this.leaderSupporters = leaderSupporters;
    this.deeds = deeds;
  }

}
