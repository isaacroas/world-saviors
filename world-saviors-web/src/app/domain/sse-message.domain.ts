export class SseMessage {

  influencerFollowers: number;
  ufoSightings: number;
  leaderSupporters: number;

  constructor(influencerFollowers: number, ufoSightings: number, leaderSupporters: number) {
    this.influencerFollowers = influencerFollowers;
    this.ufoSightings = ufoSightings;
    this.leaderSupporters = leaderSupporters;
  }

}
